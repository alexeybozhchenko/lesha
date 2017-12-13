package cs5.bozhchenko.db;

import cs5.bozhchenko.db.*;
import cs5.bozhchenko.User;

import java.util.Collection;

/**
 * Created by motorcrue on 29.10.2017.
 */
public interface UserDao {
    User create(User user) throws DatabaseException;
    void update(User user) throws DatabaseException;
    void delete(User user) throws DatabaseException;
    User find(Long id) throws DatabaseException;
    Collection findAll() throws DatabaseException;
    void setConnectionFactory(cs5.bozhchenko.db.ConnectionFactory connectionFactory);
    Collection<User> findByName(String firstName, String lastName) throws DatabaseException;
}
