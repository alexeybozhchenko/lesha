package cs5.bozhchenko.web;

/**
 * Created by motorcrue on 22.11.2017.
 */

import java.util.Properties;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import cs5.bozhchenko.db.DaoFactory;
import cs5.bozhchenko.db.MockDaoFactory;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private Mock mockUserDao;

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao());
    }
    @Override
    protected void tearDown() throws Exception {
        getMockUserDao().verify();
        super.tearDown();
    }

}