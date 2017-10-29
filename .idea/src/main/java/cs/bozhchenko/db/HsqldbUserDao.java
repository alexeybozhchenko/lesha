package cs.bozhchenko.db;

import cs5.bozhchenko.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by motorcrue on 29.10.2017.
 */
class HsqldbUserDao implements UserDao{

    private static final String INSERT_QUERY = "INSERT INTO USERS (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(){
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
    public User create(User user) throws DatabaseException {
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
        Collection result = new LinkedList();
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
        } catch (DatabaseException e) {
            throw e;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }
}
