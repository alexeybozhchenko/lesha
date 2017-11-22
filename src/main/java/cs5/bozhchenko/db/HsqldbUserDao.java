package cs5.bozhchenko.db;

import cs5.bozhchenko.db.*;
import cs5.bozhchenko.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by motorcrue on 29.10.2017.
 */
class HsqldbUserDao implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO USERS (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
    private cs5.bozhchenko.db.ConnectionFactory connectionFactory;
    private static final String FIND_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
    private static final String SELECT_BY_NAMES = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname=? AND lastname=?";
    private final String SELECT_USER_BY_ID = "SELECT id,firstname, lastname, dateofbirth FROM users WHERE id = ?";

    public HsqldbUserDao(){
        super();
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws cs5.bozhchenko.db.DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            int n = statement.executeUpdate();
            if(n!=1){
                throw new DatabaseException("Number of rows inserted: " + n);
            }
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            if(keys.next()){
                user.setId(new Long(keys.getLong(1) ));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return user;
        }
        catch (DatabaseException e) {
            throw e;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the updated rows: " + n);
            }
            statement.close();
            connection.close();
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    @Override



    public void delete(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection
                    .prepareStatement(DELETE_QUERY);
            statement.setLong(1, user.getId().longValue());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the deleted rows: " + n);
            }
            statement.close();
            connection.close();
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
       /* User user = new User();
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    user.setId(rs.getLong(1));
                    user.setFirstName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setDateOfBirth(rs.getDate(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;*/
        User result = null;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new cs5.bozhchenko.db.DatabaseException("Could not find the user with id="
                        + id);
            }
            result = new User();
            result.setId(resultSet.getLong(1));
            result.setFirstName(resultSet.getString(2));
            result.setLastName(resultSet.getString(3));
            result.setDateOfBirth(resultSet.getDate(4));
            resultSet.close();
            statement.close();
            connection.close();
        } catch (cs5.bozhchenko.db.DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new cs5.bozhchenko.db.DatabaseException(e);
        }
        return result;
    }

    @Override
    public Collection findAll() throws cs5.bozhchenko.db.DatabaseException {
        Collection <User> result = new LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while(resultSet.next()){
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
        } catch (cs5.bozhchenko.db.DatabaseException e) {
            throw e;
        }
        catch (SQLException e) {
            throw new cs5.bozhchenko.db.DatabaseException(e);
        }
        return result;
    }

    public Collection find(String firstName, String lastName)
            throws DatabaseException {
        Collection result = new LinkedList();

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection
                    .prepareStatement(SELECT_BY_NAMES);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return result;
    }
}
