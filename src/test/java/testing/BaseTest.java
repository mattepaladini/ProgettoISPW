package testing;

import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDB;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryDemo;
import com.example.progettoispw.pattern.abstractfactory.DAOFactoryFSys;
import com.example.progettoispw.utility.session.SessionManager;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;

abstract class BaseTest {

    //change this to modify the test mode (DEMO, DB or FSYS)
    protected static final String PERSISTENCE_MODE = "DEMO";

    @BeforeEach
    void initBase() throws Exception {
        resetFactory();
        SessionManager.getInstance().logout();
    }

    //force DAOFactory to use the persistence mode choose
    protected void resetFactory()  throws Exception {
        Field instanceField = DAOFactory.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, buildFactory());
    }

    private DAOFactory buildFactory(){
        return switch(PERSISTENCE_MODE.toUpperCase()){
            case "DB" -> new DAOFactoryDB();
            case "FSYS" -> new DAOFactoryFSys();

            default -> new DAOFactoryDemo();
        };
    }

}
