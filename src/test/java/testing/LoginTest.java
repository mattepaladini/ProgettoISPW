package testing;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.AuthController;
import com.example.progettoispw.controller.logic.RegistrationController;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.exception.DatabaseOperationException;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDB;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDemo;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryFSys;
import com.example.progettoispw.session.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private AuthController loginController;
    private RegistrationController registrationController;

    //Costanti per i test
    private static final String TESTUSERNAME = "testuser";
    private static final User TESTUSER = new User(TESTUSERNAME);

    //variabile di set
    //1 -> DB   2 -> DEMO   3 -> FSYS
    private static final String PERSISTENCE = "DB";

    @BeforeEach
    void setUp() throws Exception {
        // 1. Resetta l'utente loggato in memoria
        resetSessionManager();

        // 2. Forza la Factory a usare la persistenza scelta
        forceFactoryMode();

        // 3. Inizializza i controller
        loginController = new AuthController();
        registrationController = new RegistrationController();

        // 4. Pulisci il database da precedenti esecuzioni fallite
        deleteTestUser();
    }

    @AfterEach
    void tearDown() {
        // Pulisce l'utente fittizio dal DB/File alla fine del test
        deleteTestUser();

        try {
            resetSessionManager();
        } catch (Exception e) {
            System.err.println("Errore durante il reset del SessionManager: " + e.getMessage());
        }
    }


    // ==========================================
    // T04 - LOGIN CON SUCCESSO
    // ==========================================
    @Test
    @DisplayName("T04 - Login Test: credenziali corrette devono far accedere l'utente")
    void testLoginSuccess() {
        // 1. Registriamo un utente fittizio per avere i dati nel DB
        UserBean signupBean = new UserBean("Mario Rossi", " TEST_PASSWORD");
        signupBean.setUsertype(UserType.BUYER);
        registrationController.completeRegistration(signupBean);

        // Assicuriamoci che la sessione sia pulita prima di tentare il login
        assertNull(SessionManager.getInstance().getLoggedUser(), "La sessione deve essere vuota prima del login");

        User loggedUser = new User(signupBean.getUsername(), signupBean.getPassword(), signupBean.getUsertype());
        try {
            loginController.authUser(loggedUser);
        } catch (Exception e) {
            fail("Il login non doveva fallire: " + e.getMessage());
        }

        // 4. Verifiche
        assertNotNull(loggedUser, "Il metodo login deve restituire l'oggetto User");
        assertEquals(TESTUSERNAME, loggedUser.getUsername(), "L'email restituita deve combaciare con quella inserita");
        assertEquals(TESTUSERNAME, SessionManager.getInstance().getLoggedUser().getUsername());
    }

    // ==========================================
    // T05 - PASSWORD ERRATA
    // ==========================================
    @Test
    @DisplayName("T05 - Login Test: password errata deve fallire e lanciare eccezione")
    void testLoginWrongPassword() {
        // 1. Registriamo l'utente corretto
        UserBean signupBean = new UserBean("Mario Rossi", "TEST_PASSWORD");
        signupBean.setUsertype(UserType.BUYER);
        registrationController.completeRegistration(signupBean);

        User loginUser = new User(TESTUSERNAME,"PasswordSbagliata99", signupBean.getUsertype());

        // 3. Verifichiamo che venga lanciata la tua eccezione custom (sostituisci UserNotFoundException)
        assertThrows(OperationFailedException.class, () -> {
            loginController.authUser(loginUser);
        }, "Doveva essere lanciata un'eccezione per password errata");

        assertNull(SessionManager.getInstance().getLoggedUser(), "Nessun utente deve risultare loggato in sessione");
    }

    // ==========================================
    // T06 - UTENTE INESISTENTE
    // ==========================================
    @Test
    @DisplayName("T06 - Login Test: utente non registrato deve fallire e lanciare eccezione")
    void testLoginUserNotFound() {
        // Tentiamo il login senza aver mai registrato l'utente
        User loginUser = new User("fantasma@email.com", "PasswordSegreta1", UserType.BUYER);

        // Verifichiamo che venga lanciata l'eccezione
        assertThrows(OperationFailedException.class, () -> {
            loginController.authUser(loginUser);
        }, "Doveva essere lanciata un'eccezione perché l'email non esiste nel DB");
    }

    /*                  */
    //  HELPER //
    /*                  */

    private void resetSessionManager() throws NoSuchFieldException, IllegalAccessException {
        Field instance = SessionManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    private void forceFactoryMode() throws Exception {
        // Svuota l'istanza Singleton della FactoryDAO
        Field instanceField = DAOFactory.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        // Inizializza la Factory specifica in base alla costante PERSISTENCE_MODE
        DAOFactory selectedFactory = switch (PERSISTENCE.toUpperCase()) {
            case "DB" -> new DAOFactoryDB();
            case "FSYS" -> new DAOFactoryFSys();
            case "DEMO" -> new DAOFactoryDemo();
            default -> {
                System.out.println("Modalità non riconosciuta, uso DB come fallback...");
                yield new DAOFactoryDB();
            }
        };

        // Inietta la factory creata nel Singleton
        instanceField.set(null, selectedFactory);
    }private void deleteTestUser() {
        // Recupera il DAO corretto in base alla Factory forzata
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            // Chiede al DAO di eliminare fisicamente l'utente dal DB o dal File
            userDAO.deleteUser(TESTUSER);
        } catch (DatabaseOperationException e) {
            System.err.println("WARNING: Impossibile eliminare l'utente di test. Potrebbe non esistere. " + e.getMessage());
        }
    }

}
