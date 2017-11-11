package cs5.bozhchenko.db;

import cs5.bozhchenko.db.*;
import cs5.bozhchenko.User;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class HsqldbUserDaoTest extends DatabaseTestCase {
    private cs5.bozhchenko.db.HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;



    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new cs5.bozhchenko.db.ConnectionFactoryImpl("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/lesha",
                "sa",
                "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new cs5.bozhchenko.db.HsqldbUserDao(connectionFactory);
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
        } catch (cs5.bozhchenko.db.DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }

    }

    public void testUpdate() throws Exception {
        try {
            User temporaryUser = dao.find(1000L);
            assertNotNull(temporaryUser);
            temporaryUser.setFirstName("John");
            dao.update(temporaryUser);
            User updatedUser = dao.find(1000L);
            assertEquals(temporaryUser.getFirstName(), updatedUser.getFirstName());
        } catch (cs5.bozhchenko.db.DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testFind() throws Exception {
        User bush = new User();
        bush.setId(1001L);
        bush.setFirstName("George");
        bush.setLastName("Bush");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1949, Calendar.AUGUST, 17, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        bush.setDateOfBirth(calendar.getTime());

        User result = dao.find(1001L);

        assertEquals("Wrong first name",bush.getFirstName(),result.getFirstName());
        assertEquals("Wrong last name",bush.getLastName(),result.getLastName());
        assertEquals("Wrong id",bush.getId(),result.getId());
        assertEquals("Wrong date of birth",bush.getDateOfBirth(),result.getDateOfBirth());
    }

    public void testFindAll(){
        try {
            java.util.Collection collection =  dao.findAll();
            assertNotNull("Collection is null",collection);
            assertEquals("Collection size.",2,collection.size());
        } catch (cs5.bozhchenko.db.DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testDelete() throws Exception {
        try {

            User userToCheck = dao.find(1001L);
            assertNotNull(userToCheck);
            dao.delete(userToCheck);
            assertEquals(1, dao.findAll().size());
        } catch (cs5.bozhchenko.db.DatabaseException e) {
            fail(e.getMessage());
        }
    }
}