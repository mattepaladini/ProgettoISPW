package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.AuthController;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.controller.logic.RegistrationController;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.exception.DatabaseOperationException;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.utility.session.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends BaseTest {

    private AuthController loginController;
    private RegistrationController registrationController;

    private static final String TESTUSERNAME = "testuser";
    private static final String TESTPASSWORD = "testpassword";
    private static final User TESTUSER = new User(TESTUSERNAME);


    @BeforeEach
    void setUp() {


        loginController = new AuthController();
        registrationController = new RegistrationController();

        deleteTestUser();
    }

    @AfterEach
    void tearDown() {

        deleteTestUser();

        try {
            SessionManager.getInstance().logout();
        } catch (Exception e) {
            System.err.println("Error during SessionManager reset: " + e.getMessage());
        }
    }


    // ==========================================
    // REGISTRAZIONE
    // ==========================================
    @Test
    @DisplayName("T01 - Buyer Registration: valid user must be saved successfully")
    void testRegistrationBuyer() {

        UserBean signupBean = new UserBean(TESTUSERNAME, TESTPASSWORD);
        signupBean.setUsertype(UserType.BUYER);

        assertDoesNotThrow(
                () -> registrationController.completeRegistration(signupBean),
                "La registrazione non doveva lanciare eccezioni"
        );

        User saved = DAOFactory.getInstance().getUserDAO().getUserByUsername(TESTUSERNAME);
        assertNotNull(saved, "L'utente registrato deve essere recuperabile dal DAO");
        assertEquals(TESTUSERNAME, saved.getUsername());

    }

    @Test
    @DisplayName("T02 - Seller Registration: must also create the associated catalog")
    void testRegistrationSeller() {

        UserBean signupBean = new UserBean(TESTUSERNAME, TESTPASSWORD);
        signupBean.setUsertype(UserType.SELLER);

        assertDoesNotThrow(
                ()-> registrationController.completeRegistration(signupBean),
                "La registrazione seller non doveva lanciare eccezioni"
        );

        User saved = DAOFactory.getInstance().getUserDAO().getUserByUsername(TESTUSERNAME);
        assertNotNull(saved, "Il seller deve essere stato salvato");

        ManageCatalogController catalogController = new ManageCatalogController(new ManageNotificationsController());

        List<CollectableCardBean> catalog = catalogController.getSellerCards(new UserBean(TESTUSERNAME,null));
        assertNotNull(catalog, "Il catalogo del seller appena registrato non deve essere null");
        assertTrue(catalog.isEmpty(), "il catalogo di un seller appena registrato deve essere vuoto");
    }

    @Test
    @DisplayName("T03 - Registration with empty username : must throw InvalidInputException")
    void testRegistrationEmptyUsername() {
        UserBean signupBean = new UserBean("", TESTPASSWORD);
        signupBean.setUsertype(UserType.BUYER);

        assertThrows(
                InvalidInputException.class,
                ()-> registrationController.completeRegistration(signupBean),
                "Empty username must throw InvalidInputException"
        );
        assertNull(SessionManager.getInstance().getLoggedUser());
    }

    @Test
    @DisplayName("T04 - Registration with empty psw: must throw InvalidInputException")
    void testRegistrationEmptyPsw() {
        UserBean signupBean = new UserBean(TESTUSERNAME, "");
        signupBean.setUsertype(UserType.BUYER);

        assertThrows(
                InvalidInputException.class,
                ()-> registrationController.completeRegistration(signupBean),
                "Empty psw must throw InvalidInputException"
        );
        assertNull(SessionManager.getInstance().getLoggedUser());
    }



    // ==========================================
    // LOGIN
    // ==========================================
    @Test
    @DisplayName("T05 - Login with correct credentials: must start the session")
    void testLoginSuccess() {

        UserBean loginUser = new UserBean(TESTUSERNAME, TESTPASSWORD);
        loginUser.setUsertype(UserType.BUYER);
        assertDoesNotThrow(() -> registrationController.completeRegistration(loginUser));

        SessionManager.getInstance().logout();

        assertNull(SessionManager.getInstance().getLoggedUser(),
                "The session must be empty before login");

        assertDoesNotThrow(
                ()-> loginController.checkUserExist(new UserBean(TESTUSERNAME,TESTPASSWORD)),
                "Login with correct credentials must not fail"
        );

        assertNotNull(SessionManager.getInstance().getLoggedUser(),
                "Session must contain the logged user");
        assertEquals(TESTUSERNAME, SessionManager.getInstance().getLoggedUser().getUsername());
    }

    @Test
    @DisplayName("T06 - Login with wrong psw: must throw OperationFailedException")
    void testLoginWrongPsw() {

        UserBean loginUser = new UserBean(TESTUSERNAME, TESTPASSWORD);
        loginUser.setUsertype(UserType.BUYER);
        registrationController.completeRegistration(loginUser);
        SessionManager.getInstance().logout();

        UserBean wrongPswUser = new UserBean(TESTUSERNAME, "wrong");

        assertThrows(
                OperationFailedException.class,
                ()-> loginController.checkUserExist(wrongPswUser),
                "Login with wrong psw must throw OperationFailedException"
        );

        assertNull(SessionManager.getInstance().getLoggedUser(),
                "No user should be logged after a failed login");

    }

    @Test
    @DisplayName("T07 - Login with non-existent username: must throw OperationFailedException")
    void testLoginNonExistentUsername() {
        assertThrows(
                OperationFailedException.class,
                ()-> loginController.checkUserExist(new UserBean("aaaa", TESTPASSWORD)),
                "Non existent username must throw OperationFailedException"
        );
        assertNull(SessionManager.getInstance().getLoggedUser());
    }


    //  HELPER //
    private void deleteTestUser() {

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {

            userDAO.deleteUser(TESTUSER);
        } catch (DatabaseOperationException e) {
            System.err.println("WARNING: Unable to delete test user. It may not exist.. " + e.getMessage());
        }
    }

}
