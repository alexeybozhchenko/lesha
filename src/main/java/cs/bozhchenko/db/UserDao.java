package cs.bozhchenko.db;

import cs5.bozhchenko.User;

import java.util.Collection;

/**
 * Created by motorcrue on 29.10.2017.
 */
public interface UserDao {
    User create(User user) throws DatabaseException;
    User update(User user) throws DatabaseException;
    void delete(User user) throws DatabaseException;
    User find(Long id) throws DatabaseException;
    Collection findAll() throws DatabaseException;
}
