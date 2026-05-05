package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.utility.session.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardCatalogTest extends BaseTest {

    private ManageCatalogController catalogController;
    private ManageNotificationsController notificationsController;
    private static final String TEST_CARD_NAME = "Drago Bianco Occhi Blu test";

    private static final Card TEST_CARD = new Card(
            "Drago Bianco Occhi Blu test",
            100f,
            Gradation.PERFETTO,
            "testseller",
            1,
            Attribute.LUCE,
            Type.MOSTRO
    );
    private static final String TEST_BUYER = "testbuyer";
    private static final String TEST_SELLER = "testseller";

    @BeforeEach
    void setUp() {

        notificationsController = new ManageNotificationsController();
        catalogController = new ManageCatalogController(notificationsController);
        notificationsController.followSeller(TEST_BUYER, TEST_SELLER);
        SessionManager.getInstance().setLoggedUser(new Seller(TEST_SELLER, "psw"));
        deleteTestCard();
    }

    @AfterEach
    void tearDown(){
        deleteTestCard();
        SessionManager.getInstance().logout();
    }


    // ==========================================
    // ADD CARD
    // ==========================================
    @Test
    @DisplayName("T01 - Add Card: Inserting a new card into the catalog")
    void testAddCard() {


        assertDoesNotThrow(
                ()-> catalogController.addCard(buildTestCardBean(), TEST_SELLER),
                "Inserting a new card should not throw an exception"
        );

        List<CollectableCardBean> cards = catalogController.getSellerCards(
                new UserBean(TEST_SELLER, null)
        );
        assertTrue(
                cards.stream().anyMatch(c->c.getName().equals(TEST_CARD.getName())),
                "The new added card must be in the catalog"
        );
    }

    @Test
    @DisplayName("T02 - Add Card with the same name in a catalog: adding a 'duplicate' card in the same catalog")
    void testAddCardWithSameName() {

        CollectableCardBean cardBean = buildTestCardBean();

        assertDoesNotThrow(
                ()-> catalogController.addCard(cardBean, TEST_SELLER),
                "The first adding should not throw an exception"
        );

        CollectableCardBean duplicateBean = buildTestCardBean();
        assertThrows(
                OperationFailedException.class,
                ()-> catalogController.addCard(duplicateBean, TEST_SELLER),
                "The second adding must throw OperationFailedException"
        );
    }

    @Test
    @DisplayName("T03 - Add card with empty name: must throw OperationFailedException")
    void testAddCardWithEmptyName() {
       CollectableCardBean cardBean = buildTestCardBean();
       cardBean.setName("");

       assertThrows(
               OperationFailedException.class,
               ()-> catalogController.addCard(cardBean, TEST_SELLER),
               "Empty name must throw OperationFailedException"
       );

    }

    @Test
    @DisplayName("T04 - Add Card with price zero: must throw OperationFailedException")
    void testAddCardZeroPrice() {
        CollectableCardBean cardBean = new CollectableCardBean();
        cardBean.setName(TEST_CARD_NAME);
        cardBean.setPrice(0f);
        cardBean.setGradation(TEST_CARD.getGradation());
        cardBean.setType(TEST_CARD.getType());
        cardBean.setAttribute(TEST_CARD.getAttribute());
        cardBean.setLevel(TEST_CARD.getLevel());
        cardBean.setSeller(TEST_SELLER);

        assertThrows(
                OperationFailedException.class,
                () -> catalogController.addCard(cardBean, TEST_SELLER),
                "Price zero must be throw OperationFailedException"
        );
    }

    // ==========================================
    // REMOVE CARD
    // ==========================================

    @Test
    @DisplayName("T05 - Remove Card: removing a card from the catalog")
    void testRemoveCard() {
        CollectableCardBean cardBean = buildTestCardBean();

        assertDoesNotThrow(
                () -> catalogController.addCard(cardBean, TEST_SELLER),
                "The preliminary addition was not supposed to throw exceptions"
        );

        assertDoesNotThrow(
                () -> catalogController.removeCardFromCatalog(cardBean, TEST_SELLER),
                "The removal should not have thrown exceptions"
        );

        List<CollectableCardBean> cards = catalogController.getSellerCards(
                new UserBean(TEST_SELLER, null)
        );
        assertFalse(
                cards.stream().anyMatch(c -> c.getName().equals(TEST_CARD_NAME)),
                "The removed card must no longer be present in the catalog"
        );
    }

    // ==========================================
    // UPDATE PRICE CARD
    // ==========================================

    @Test
    @DisplayName("T07 - Update Price: the price card must correctly update")
    void testUpdatePrice() {
        CollectableCardBean cardBean = buildTestCardBean();
        catalogController.addCard(cardBean, TEST_SELLER);

        float newPrice = 250f;
        assertDoesNotThrow(
                () -> catalogController.updateCardPrice(cardBean, newPrice),
                "The price update should not throw an exception"
        );

        List<CollectableCardBean> cards = catalogController.getSellerCards(
                new UserBean(TEST_SELLER, null)
        );
        CollectableCardBean updated = cards.stream()
                .filter(c -> c.getName().equals(TEST_CARD_NAME))
                .findFirst()
                .orElse(null);

        assertNotNull(updated, "The card must still be present after the update");
        assertEquals(newPrice, updated.getPrice(), 0.01f,
                "The price must match the new value set");
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

    private CollectableCardBean buildTestCardBean(){
        CollectableCardBean bean = new CollectableCardBean();
        bean.setName(TEST_CARD.getName());
        bean.setPrice(TEST_CARD.getPrice());
        bean.setGradation(TEST_CARD.getGradation());
        bean.setType(TEST_CARD.getType());
        bean.setAttribute(TEST_CARD.getAttribute());
        bean.setLevel(TEST_CARD.getLevel());
        bean.setSeller(TEST_SELLER);
        return bean;
    }

}
