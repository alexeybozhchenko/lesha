package cs5.bozhchenko.cs.bozhchenko.db;

import cs5.bozhchenko.User;

import java.util.Collection;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class HsqldbUserDao {
    User create(User user) throws DatabaseException{return null;};
    User update(User user) throws DatabaseException{};
    void delete(User user) throws DatabaseException{};
    User find(Long id) throws DatabaseException{};
    Collection findAll() throws DatabaseException{};
}