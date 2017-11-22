package cs5.bozhchenko.db;

import cs5.bozhchenko.db.*;
import cs5.bozhchenko.User;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import java.util.*;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class HsqldbUserDaoTest extends DatabaseTestCase {
    private cs5.bozhchenko.db.HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;
    private static final long USERS_ID = 1000L;
    private static final int DAY_OF_BIRTH = 4;
    private static final int MONTH_OF_BIRTH = 11;
    private static final int YEAR_OF_BIRTH = 1997;
    private static final String LAST_NAME = "Diego";
    private static final String FIRST_NAME = "Spinosa";


    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new cs5.bozhchenko.db.ConnectionFactoryImpl("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/JavaLW1",
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
        Collection<User> collection = new LinkedList<>();
        try {
            collection = dao.findAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        assertNotNull("Collection is null", collection);
        assertEquals("Collection size.", 2, collection.size());
    }

    public void testDelete() throws Exception {
        Collection<User> collection = new LinkedList<>();
        User user = new User();
        user.setId(USERS_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        Calendar dateOfBirthNew = new GregorianCalendar(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
        user.setDateOfBirth(dateOfBirthNew.getTime());
        try {
            dao.delete(user);
            collection = dao.findAll();
            assertNotNull("Collection is null", collection);
            assertEquals("Collection size.", 1, collection.size());

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}