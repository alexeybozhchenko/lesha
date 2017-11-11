package cs5.bozhchenko.db;

/**
 * Created by motorcrue on 11.11.2017.
 */
public class DaoFactoryImpl extends DaoFactory {

@Override
    public UserDao getUserDao(){
    HsqldbUserDao userDao;
        try {
            Class<?> class1 = Class.forName(properties.getProperty(USER_DAO));
            userDao = (HsqldbUserDao) class1.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userDao;
}
}
