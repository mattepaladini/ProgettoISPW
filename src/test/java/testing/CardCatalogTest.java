package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
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
    private ManageNotificationsController notificationsController;
    private static final Card TEST_CARD = new Card(
            "Dragp Bianco Occhi Blu test",
            100f,
            Gradation.PERFETTO,
            "testseller",
            1,
            Attribute.LUCE,
            Type.MOSTRO
    );
    private static final String PERSISTENCE_MODE = "DEMO"; //
    private static final String TEST_BUYER = "testbuyer";

    @BeforeEach
    void setUp() throws Exception {
        forceFactoryMode();
        catalogController = new ManageCatalogController();
        notificationsController = new ManageNotificationsController();
        deleteTestCard();
    }

    @AfterEach
    void tearDown(){
        deleteTestCard();
    }

    @Test
    @DisplayName("T01 - Add Card: Inserimento di una nuova carta nel catalogo")
    void testAddCard() {

        CollectableCardBean cardBean = new CollectableCardBean();
        cardBean.setName(TEST_CARD.getName());
        cardBean.setPrice(TEST_CARD.getPrice());
        cardBean.setGradation(TEST_CARD.getGradation());
        cardBean.setType(TEST_CARD.getType());
        cardBean.setAttribute(TEST_CARD.getAttribute());
        cardBean.setLevel(TEST_CARD.getLevel());
        cardBean.setSeller(TEST_CARD.getSeller());

        notificationsController.followSeller(TEST_BUYER, TEST_CARD.getSeller());

        try {
            catalogController.addCard(cardBean, TEST_CARD.getSeller());
        }catch (Exception e) {
            fail("L'aggiunta della carta non doveva lanciare eccezioni: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("T02 - Add Card with the same name in a catalog: aggiunta di una carta 'doppione' nello stesso catalogo")
    void testAddCardWithSameName() {

        CollectableCardBean cardBean1 = new CollectableCardBean();
        cardBean1.setName(TEST_CARD.getName());
        cardBean1.setPrice(TEST_CARD.getPrice());
        cardBean1.setGradation(TEST_CARD.getGradation());
        cardBean1.setType(TEST_CARD.getType());
        cardBean1.setAttribute(TEST_CARD.getAttribute());
        cardBean1.setLevel(TEST_CARD.getLevel());
        cardBean1.setSeller(TEST_CARD.getSeller());

        CollectableCardBean cardBean2 = new CollectableCardBean();
        cardBean2.setName(TEST_CARD.getName());
        cardBean2.setPrice(TEST_CARD.getPrice());
        cardBean2.setGradation(TEST_CARD.getGradation());
        cardBean2.setType(TEST_CARD.getType());
        cardBean2.setAttribute(TEST_CARD.getAttribute());
        cardBean2.setLevel(TEST_CARD.getLevel());
        cardBean2.setSeller(TEST_CARD.getSeller());

        notificationsController.followSeller(TEST_BUYER, TEST_CARD.getSeller());

        String seller = TEST_CARD.getSeller();
        try {
            catalogController.addCard(cardBean1, seller);
        }catch (Exception e) {
            fail("L'aggiunta della carta non doveva lanciare eccezioni: " + e.getMessage());
        }

        assertThrows(OperationFailedException.class, () -> catalogController.addCard(cardBean2, seller), "Doveva essere lanciata un'eccezione perché la carta è un doppione esatto!");
    }

    @Test
    @DisplayName("T03 - Remove Card")
    void testRemoveCard(){
        CollectableCardBean cardBean = new CollectableCardBean();
        cardBean.setName(TEST_CARD.getName());
        cardBean.setPrice(TEST_CARD.getPrice());
        cardBean.setGradation(TEST_CARD.getGradation());
        cardBean.setType(TEST_CARD.getType());
        cardBean.setAttribute(TEST_CARD.getAttribute());
        cardBean.setLevel(TEST_CARD.getLevel());
        cardBean.setSeller(TEST_CARD.getSeller());

        notificationsController.followSeller(TEST_BUYER, TEST_CARD.getSeller());


        try {
            catalogController.addCard(cardBean, TEST_CARD.getSeller());
        }catch (Exception e) {
            fail("L'aggiunta della carta non doveva lanciare eccezioni: " + e.getMessage());
        }

        try{
            catalogController.removeCardFromCatalog(cardBean, TEST_CARD.getSeller());
        }catch (Exception e) {
            fail("Il metodo removeCardFromCatalog ha lanciato un'eccezione: "+e.getMessage());
        }

    }



    //*****************************//
    // HELPER //

    private void deleteTestCard() {
        try {
            // Chiamata diretta al DAO per fare pulizia fisica (crea il metodo nel DAO se non esiste)
            DAOFactory.getInstance().getCardCatalogDAO().removeCard(TEST_CARD, TEST_CARD.getSeller());
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
