package cs5.bozhchenko.servlet;

import cs5.bozhchenko.User;
import cs5.bozhchenko.web.MockServletTestCase;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by motorcrue on 22.11.2017.
 */
public class BrowseServletTest extends MockServletTestCase {
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(1000L, "Simon", "Cat", new Date());
        List list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Could not find list of users in session", collection);
        assertSame(list, collection);
    }
    public void testDelete() {
        User user = new User(1000L, "Simon", "Cat", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        getMockUserDao().expect("delete", user);

        addRequestParameter("id", user.getId().toString());
        addRequestParameter("delete", "Delete");
        doPost();
    }

    public void testEdit() {
        User user = new User(1000L, "Simon", "Cat", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", user);
        assertSame(user, userInSession);
    }

    public void testEditWithoutId() {
        User user = new User(1000L, "Simon", "Cat", new Date());
        addRequestParameter("editButton", "Edit");
        doPost();
        assertNotNull("Could not find error message", getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }
}