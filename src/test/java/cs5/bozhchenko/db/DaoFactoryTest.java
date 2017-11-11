package cs5.bozhchenko.db;

import junit.framework.TestCase;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class DaoFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    public void testGetUserDao() throws Exception {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            UserDao userDao = daoFactory.getUserDao();
            assertNotNull("UserDao instance is null", userDao);
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}