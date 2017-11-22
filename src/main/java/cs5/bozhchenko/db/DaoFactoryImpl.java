package cs5.bozhchenko.db;

/**
 * Created by motorcrue on 11.11.2017.
 */
public class DaoFactoryImpl extends DaoFactory {

@Override
    public UserDao getUserDao(){
    UserDao result = null;
        try {
            Class class1 = Class.forName(properties.getProperty(USER_DAO));
            result = (UserDao) class1.newInstance();
            result.setConnectionFactory(getConnectionFactory());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return result;
}
}
