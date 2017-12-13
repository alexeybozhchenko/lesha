package cs5.bozhchenko.db;
import cs5.bozhchenko.User;
import cs5.*;
import cs5.bozhchenko.db.ConnectionFactory;
import cs5.bozhchenko.db.DatabaseException;
import cs5.bozhchenko.db.UserDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {
    private Long id = 0L;
    private Map<Long,User> users = new HashMap<>();
    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }


    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }

    public Collection findByName(String firstName, String lastName) throws DatabaseException {
        Collection<User> foundUsers = new LinkedList<>();
        for (Map.Entry<Long, User> user : users.entrySet()) {
            if (user.getValue().getFirstName().equals(firstName) && user.getValue().getLastName().equals(lastName)) {
                foundUsers.add(user.getValue());
            }
        }
        return foundUsers;
    }
}
