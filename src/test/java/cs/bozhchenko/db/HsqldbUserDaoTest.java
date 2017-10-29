package cs.bozhchenko.db;

import cs5.bozhchenko.User;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class HsqldbUserDaoTest extends TestCase {
    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl();
        dao = new HsqldbUserDao(connectionFactory);
    }

    public void testCreate() throws Exception {
        try {
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setDateOfBirth(new Date());
            assertNull(user.getId());
            user = dao.create(user);
            assertNotNull(user);
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }

    }

}