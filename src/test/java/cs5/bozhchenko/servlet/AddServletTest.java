package cs5.bozhchenko.servlet;

import cs5.bozhchenko.User;
import cs5.bozhchenko.web.MockServletTestCase;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by motorcrue on 22.11.2017.
 */
public class AddServletTest extends MockServletTestCase {
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    public void testAdd() {
        Date date = new Date();
        User newUser = new User("Simon", "Cat", date);
        User user = new User(1000L, "Simon", "Cat", date);
        getMockUserDao().expectAndReturn("create", newUser, user);

        addRequestParameter("firstName", "Simon");
        addRequestParameter("lastName", "Cat");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }

    public void testAddEmptyFirstName() {
        Date date = new Date();
        addRequestParameter("lastName", "Cat");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyLastName() {
        Date date = new Date();
        addRequestParameter("firstName", "Simon");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyDate() {
        addRequestParameter("firstName", "Simon");
        addRequestParameter("lastName", "Cat");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyDateIncorrect() {
        Date date = new Date();
        addRequestParameter("firstName", "Simon");
        addRequestParameter("lastName", "Cat");
        addRequestParameter("date", "xyz");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }
}