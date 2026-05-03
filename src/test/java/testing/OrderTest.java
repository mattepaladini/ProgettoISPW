package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDB;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDemo;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryFSys;
import com.example.progettoispw.utility.session.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static final String PERSISTENCE_MODE = "FSYS"; // Usa DB o FSYS
    private BuyController buyController;
    private SessionManager sessionManager;

    private static final Card TEST_CARD_ORDER = new Card(
            "Dragp Bianco Occhi Blu test",
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
    void setUp() throws Exception {
        forceFactoryMode();
        buyController = new BuyController();
        sessionManager = SessionManager.getInstance();
        deleteTestOrder();
    }

    @AfterEach
    void tearDown(){
        deleteTestOrder();
    }


    @Test
    @DisplayName("T07 - Save Order")
     void testSaveOrder() {
        
        sessionManager.getShoppingCart().add(TEST_CARD_ORDER);

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(TEST_ORDER.getId());

        TEST_CARD_ORDERBEAN.setName(TEST_CARD_ORDER.getName());
        TEST_CARD_ORDERBEAN.setPrice(TEST_CARD_ORDER.getPrice());

        orderBean.setCards(List.of(TEST_CARD_ORDERBEAN));
        orderBean.setTotale(TEST_ORDER.getTotale());
        orderBean.setShippingAddress(TEST_ORDER.getIndirizzoSpedizione());
        orderBean.setNameSurname(TEST_CARD_ORDER.getName());
        orderBean.setCityName("Roma");
        orderBean.setPaymentCard("1111 1111 1111 1111");
        orderBean.setCvv("123");

        User loggedUser = new User(TEST_ORDER.getCompratore());

        try{

            buyController.compileOrder(orderBean ,loggedUser.getUsername());
        } catch (Exception e) {
            fail("L'operazione compileOrder non doveva lanciare eccezioni: " + e.getMessage());
        }

        try{
            List<Order> ordiniUtente = DAOFactory.getInstance()
                    .getOrderDAO()
                    .getOrdersByUser(loggedUser);

            assertNotNull(ordiniUtente, "La lista degli ordini non deve essere null");

            // Asserzione 2: La lista non deve essere vuota
            assertFalse(ordiniUtente.isEmpty(), "La lista degli ordini non può essere vuota dopo il salvataggio");

            boolean ordineTrovato = ordiniUtente.stream()
                    .anyMatch(o -> o.getId()==(TEST_ORDER.getId())); // Usa getOrderId() se si chiama così

            assertTrue(ordineTrovato, "L'ordine appena salvato (" + TEST_ORDER.getId() + ") non è stato trovato nel sistema!");

        } catch (Exception e) {
            fail("L'operazione compileOrder non doveva lanciare eccezioni: " + e.getMessage());
        }

    }


    // *******************//
    //
    // *******************//

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

    private void deleteTestOrder(){

        try{
            DAOFactory.getInstance().getOrderDAO().deleteOrder(TEST_ORDER);
        } catch (Exception e) {
            System.err.println("WARNING: Impossibile eliminare la carta di test: " + e.getMessage());
        }
    }
}
