package cs.bozhchenko.db;

import cs5.bozhchenko.User;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class HsqldbUserDaoTest extends TestCase {
    HsqldbUserDao dao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao();
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