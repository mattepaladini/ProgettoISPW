package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.controller.logic.ManageCartController;
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

class OrderTest extends BaseTest{

    private static final String TEST_BUYER  = "testbuyer";
    private static final String TEST_SELLER = "testseller";
    private static final String TEST_CARD_NAME = "Drago Bianco Occhi Blu test";

    private BuyController buyController;
    private ManageCartController manageCartController;


    private static final Card TEST_CARD_ORDER = new Card(
            "Drago Bianco Occhi Blu test",
            100f,
            Gradation.PERFETTO,
            "testseller",
            1,
            Attribute.LUCE,
            Type.MOSTRO
    );
    private static final CollectableCardBean TEST_CARD_ORDERBEAN = new CollectableCardBean();
    private static final Order TEST_ORDER = new Order(
            0001,
            List.of(TEST_CARD_ORDER),
            "Via Roma 10",
            "testbuyer",
            45f,
            "10/09/2025"
    );

    @BeforeEach
    void setUp() {
        manageCartController = new ManageCartController();
        buyController = new BuyController();
        SessionManager.getInstance().setLoggedUser(new User(TEST_BUYER));
        deleteTestOrder();
    }

    @AfterEach
    void tearDown(){
        deleteTestOrder();
        SessionManager.getInstance().logout();
    }

    // ==========================================
    // ADD CARD TO CART
    // ==========================================

    @Test
    @DisplayName("T01 - Add to cart: adding a card to cart")
     void testAddToCart() {
        
        CollectableCardBean cardBean = buildTestCardBean();
        boolean result = manageCartController.addToCart(cardBean);

        assertTrue(result, "Adding must be return true");

        List<CollectableCardBean> cart = manageCartController.getCardsFromCart();
        assertFalse(cart.isEmpty(), "Cart should not be empty after adding");

        assertEquals(TEST_CARD_NAME, cart.get(0).getName(),
                "Cart name should be the same");

    }

    // ==========================================
    // REMOVE CARD TO CART
    // ==========================================
    @Test
    @DisplayName("T02 - Remove From Cart: removal card from cart")
    void testRemoveFromCart() {
        CollectableCardBean cardBean = buildTestCardBean();
        manageCartController.addToCart(cardBean);

        boolean result = manageCartController.removeFromCart(cardBean);
        assertTrue(result, "Removing must be return true");

        List<CollectableCardBean> cart = manageCartController.getCardsFromCart();
        assertTrue(cart.isEmpty(), "Cart should be empty after removing");
    }

    // ==========================================
    // CHECKOUT
    // ==========================================
    @Test
    @DisplayName("T03 - Compile Order: valid order must be saved correctly")
    void testCompileOrder() {

        manageCartController.addToCart(buildTestCardBean());
        OrderBean orderBean = buildValidOrderBean();

        assertDoesNotThrow(
                ()-> buyController.compileOrder(orderBean, TEST_BUYER),
                "Valid checkout should not throw an exception"
        );

        List<Order> orders = DAOFactory.getInstance().getOrderDAO().getOrdersByUser(new User(TEST_BUYER));

        assertNotNull(orders, "Orders should not be null");
        assertFalse(orders.isEmpty(), "Orders should not be empty after checkout");

        assertTrue(manageCartController.getCardsFromCart().isEmpty(),
                "Cart must be empty after checkout");
    }


    @Test
    @DisplayName("T05 - Compile Order with empty cart: must throw OperationFailedException")
    void testCompileOrderEmptyCart() {

        OrderBean orderBean = buildValidOrderBean();

        assertThrows(
                OperationFailedException.class,
                () -> buyController.compileOrder(orderBean, TEST_BUYER),
                "Checkout with epmty cart"
        );
    }


    @Test
    @DisplayName("T06 - Compile Order with missing mandatory fields: must throw OperationFailedException")
    void testCompileOrderMissingFields() {
        manageCartController.addToCart(buildTestCardBean());

        OrderBean incompleteBean = new OrderBean();
        incompleteBean.setShippingAddress("Via Roma 10");
        incompleteBean.setNameSurname("");
        incompleteBean.setCityName("");
        incompleteBean.setPaymentCard("");
        incompleteBean.setCvv("");

        assertThrows(
                OperationFailedException.class,
                () -> buyController.compileOrder(incompleteBean, TEST_BUYER),
                "Missing mandatory fields"
        );
    }


    // *******************//
    //
    // *******************//

    private CollectableCardBean buildTestCardBean() {
        CollectableCardBean bean = new CollectableCardBean();
        bean.setName(TEST_CARD_ORDER.getName());
        bean.setPrice(TEST_CARD_ORDER.getPrice());
        bean.setGradation(TEST_CARD_ORDER.getGradation());
        bean.setType(TEST_CARD_ORDER.getType());
        bean.setAttribute(TEST_CARD_ORDER.getAttribute());
        bean.setLevel(TEST_CARD_ORDER.getLevel());
        bean.setSeller(TEST_SELLER);
        return bean;
    }

    private OrderBean buildValidOrderBean() {
        OrderBean orderBean = new OrderBean();
        orderBean.setNameSurname("Mario Rossi");
        orderBean.setCityName("Roma");
        orderBean.setShippingAddress("Via Roma 10");
        orderBean.setPaymentCard("1111 2222 3333 4444");
        orderBean.setCvv("123");
        return orderBean;
    }

    private void deleteTestOrder(){

        try{
            DAOFactory.getInstance().getOrderDAO().deleteOrder(TEST_ORDER);
        } catch (Exception e) {
            System.err.println("WARNING: Impossibile eliminare la carta di test: " + e.getMessage());
        }
    }
}
