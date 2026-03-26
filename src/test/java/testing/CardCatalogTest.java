package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDB;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDemo;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryFSys;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class CardCatalogTest {

    private ManageCatalogController catalogController;
    private static final Card TEST_CARD = new Card(
            "Dragp Bianco Occhi Blu test",
            100f,
            Gradazione.PERFETTO,
            "testseller",
            1,
            Attribute.LUCE,
            Type.MOSTRO
    );
    private static final String PERSISTENCE_MODE = "FSYS"; // Usa DB o FSYS

    @BeforeEach
    void setUp() throws Exception {
        forceFactoryMode();
        catalogController = new ManageCatalogController();
        deleteTestCard();
    }

    @AfterEach
    void tearDown(){
        deleteTestCard();
    }

    @Test
    @DisplayName("T07 - Add Card: Inserimento di una nuova carta nel catalogo")
    void testAddCard() {

        CollectableCardBean cardBean = new CollectableCardBean();
        cardBean.setNomeCarta(TEST_CARD.getNome());
        cardBean.setPrezzoCorrente(TEST_CARD.getPrezzoAttuale());
        cardBean.setGradazione(TEST_CARD.getGradazione());
        cardBean.setTipo(TEST_CARD.getTipo());
        cardBean.setAttributo(TEST_CARD.getAttributo());
        cardBean.setLivello(TEST_CARD.getLivello());
        cardBean.setVenditore(TEST_CARD.getVenditore());

        User seller = new User(TEST_CARD.getVenditore());
        
        try {
            catalogController.addCard(cardBean, seller);
        }catch (Exception e) {
            fail("L'aggiunta della carta non doveva lanciare eccezioni: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("T08 - Add Card with the same name in a catalog: aggiunta di una carta 'doppione' nello stesso catalogo")
    void testAddCardWithSameName() {

        CollectableCardBean cardBean1 = new CollectableCardBean();
        cardBean1.setNomeCarta(TEST_CARD.getNome());
        cardBean1.setPrezzoCorrente(TEST_CARD.getPrezzoAttuale());
        cardBean1.setGradazione(TEST_CARD.getGradazione());
        cardBean1.setTipo(TEST_CARD.getTipo());
        cardBean1.setAttributo(TEST_CARD.getAttributo());
        cardBean1.setLivello(TEST_CARD.getLivello());
        cardBean1.setVenditore(TEST_CARD.getVenditore());

        CollectableCardBean cardBean2 = new CollectableCardBean();
        cardBean2.setNomeCarta(TEST_CARD.getNome());
        cardBean2.setPrezzoCorrente(TEST_CARD.getPrezzoAttuale());
        cardBean2.setGradazione(TEST_CARD.getGradazione());
        cardBean2.setTipo(TEST_CARD.getTipo());
        cardBean2.setAttributo(TEST_CARD.getAttributo());
        cardBean2.setLivello(TEST_CARD.getLivello());
        cardBean2.setVenditore(TEST_CARD.getVenditore());

        User seller = new User(TEST_CARD.getVenditore());
        try {
            catalogController.addCard(cardBean1, seller);
        }catch (Exception e) {
            fail("L'aggiunta della carta non doveva lanciare eccezioni: " + e.getMessage());
        }

        assertThrows(OperationFailedException.class, () -> {

            catalogController.addCard(cardBean2, seller);

        }, "Doveva essere lanciata un'eccezione perché la carta è un doppione esatto!");
    }

    @Test
    @DisplayName("T09 - Remove Card")
    void testRemoveCard(){
        CollectableCardBean cardBean = new CollectableCardBean();
        cardBean.setNomeCarta(TEST_CARD.getNome());
        cardBean.setPrezzoCorrente(TEST_CARD.getPrezzoAttuale());
        cardBean.setGradazione(TEST_CARD.getGradazione());
        cardBean.setTipo(TEST_CARD.getTipo());
        cardBean.setAttributo(TEST_CARD.getAttributo());
        cardBean.setLivello(TEST_CARD.getLivello());
        cardBean.setVenditore(TEST_CARD.getVenditore());

        User seller = new User(TEST_CARD.getVenditore());

        try {
            catalogController.addCard(cardBean, seller);
        }catch (Exception e) {
            fail("L'aggiunta della carta non doveva lanciare eccezioni: " + e.getMessage());
        }

        try{
            catalogController.removeCardFromCatalog(cardBean, seller);
        }catch (Exception e) {
            fail("Il metodo removeCardFromCatalog ha lanciato un'eccezione: "+e.getMessage());
        }

    }



    //*****************************//
    // HELPER //

    private void deleteTestCard() {
        try {
            // Chiamata diretta al DAO per fare pulizia fisica (crea il metodo nel DAO se non esiste)
            DAOFactory.getInstance().getCardCatalogDAO().removeCard(TEST_CARD, TEST_CARD.getVenditore());
        } catch (Exception e) {
            System.err.println("WARNING: Impossibile eliminare la carta di test: " + e.getMessage());
        }
    }

    private void forceFactoryMode() throws Exception {
        Field instanceField = DAOFactory.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, switch (PERSISTENCE_MODE.toUpperCase()) {
            case "DB" -> new DAOFactoryDB();
            case "FSYS" -> new DAOFactoryFSys();
            case "DEMO" -> new DAOFactoryDemo();
            default -> new DAOFactoryDemo();
        });
    }
}
