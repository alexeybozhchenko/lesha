package cs.bozhchenko.db;

import cs5.bozhchenko.User;

import java.util.Collection;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class HsqldbUserDao implements UserDao{

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        return null;
    }

    @Override
    public User update(User user) throws DatabaseException {
        return null;
    }

    @Override
    public void delete(User user) throws DatabaseException {

    }

    @Override
    public User find(Long id) throws DatabaseException {
        return null;
    }

    @Override
    public Collection findAll() throws DatabaseException {
        return null;
    }
}
