import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by motorcrue on 26.09.2017.
 */
public class UserTest extends TestCase {

    private cs5.bozhchenko.User user;
    private Date dateOfBirth;

    public void setUp() throws Exception {
        super.setUp();
        user = new cs5.bozhchenko.User();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1984, Calendar.MAY, 26);
        dateOfBirth = calendar.getTime();
    }
public void testGetFullName(){
        user.setFirstName("John");
        user.setLastName("Doe");
        assertEquals("Doe, John", user.getFullName());
}

public void testGetAge(){
    final int year = 1984;
    user.setDateOfBirth(dateOfBirth);
    assertEquals(2017-year, user.getAge());
}
}