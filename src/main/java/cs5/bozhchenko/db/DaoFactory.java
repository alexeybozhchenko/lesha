package cs5.bozhchenko.db;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by motorcrue on 29.10.2017.
 */
public abstract class DaoFactory {
    protected static final String USER_DAO = "cs5.bozhchenko.db.UserDao";
    private static final java.lang.String DAO_FACTORY ="dao.factory" ;
    protected static Properties properties;

    private static DaoFactory instance;

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static synchronized DaoFactory getInstance(){
        if (instance == null) {
            Class factoryClass;
            try {
                 factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    protected DaoFactory(){

    }

    public static void init(Properties prop){
        properties = prop;
        instance = null;
    }

    protected ConnectionFactory getConnectionFactory(){
        return new ConnectionFactoryImpl(properties);
        }
    public abstract UserDao getUserDao();
}
