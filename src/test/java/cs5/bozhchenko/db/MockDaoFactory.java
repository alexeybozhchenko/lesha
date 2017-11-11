package cs5.bozhchenko.db;

import com.mockobjects.dynamic.Mock;
import cs5.bozhchenko.db.DaoFactory;
import cs5.bozhchenko.db.UserDao;

public abstract class MockDaoFactory extends DaoFactory {
    private Mock mockUserDao;

    public MockDaoFactory() {
        super();
        mockUserDao = new Mock(UserDao.class);


    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public UserDao getUserDao() {
        return  (UserDao) mockUserDao.proxy();
    }

}