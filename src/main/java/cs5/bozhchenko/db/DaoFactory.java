package cs5.bozhchenko.db;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by motorcrue on 29.10.2017.
 */
public abstract class DaoFactory {
    protected static final String USER_DAO = "cs.bozhchenko.db.UserDao";
    private static final java.lang.String DAO_FACTORY ="dao.factory" ;
    protected static Properties properties;

    private static DaoFactory INSTANCE;

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static synchronized DaoFactory getInstance(){
        if (INSTANCE == null) {
            try {
                Class<?> factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                INSTANCE = (DaoFactory) factoryClass.newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    protected DaoFactory(){

    }

    public static void init(Properties prop){
        properties=prop;
        INSTANCE = null;
    }

    protected ConnectionFactory getConnectionFactory(){
        return new ConnectionFactoryImpl(properties);
        }
    public abstract UserDao getUserDao();
}
