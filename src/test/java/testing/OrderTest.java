package testing;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.model.*;
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
import java.util.List;

public class OrderTest {

    private static final String PERSISTENCE_MODE = "FSYS"; // Usa DB o FSYS
    private BuyController buyController;
    private SessionManager sessionManager;

    private static final Card TEST_CARD_ORDER = new Card(
            "Dragp Bianco Occhi Blu test",
            100f,
            Gradazione.PERFETTO,
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
    @DisplayName("T10 - Save Order")
    public void testSaveOrder() {
        
        sessionManager.getShoppingCart().add(TEST_CARD_ORDER);

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(TEST_ORDER.getId());
        TEST_CARD_ORDERBEAN.setNomeCarta(TEST_CARD_ORDER.getNome());
        TEST_CARD_ORDERBEAN.setPrezzoCorrente(TEST_CARD_ORDER.getPrezzoAttuale());
        orderBean.setCards(List.of(TEST_CARD_ORDERBEAN));
        orderBean.setTotale(TEST_ORDER.getTotale());
        orderBean.setShippingAddress(TEST_ORDER.getIndirizzoSpedizione());
        orderBean.setNameSurname(TEST_CARD_ORDER.getNome());
        orderBean.setCityName("Roma");
        orderBean.setPaymentCard("1111 1111 1111 1111");
        orderBean.setCvv("123");

        User loggedUser = new User(TEST_ORDER.getCompratore());

        try{

            buyController.compileOrder(orderBean ,loggedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
