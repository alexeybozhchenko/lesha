package cs5.bozhchenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class ConnectionFactoryImpl implements ConnectionFactory{

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(String driver, String url, String user, String password) {
        this.driver=driver;
        this.password=password;
        this.url=url;
        this.user=user;
    }

    public ConnectionFactoryImpl(Properties properties) {
         user = properties.getProperty("connection.user");
         password = properties.getProperty("connection.password");
         url = properties.getProperty("connection.url");
         driver = properties.getProperty("connection.driver");
    }


    public Connection createConnection() throws DatabaseException{
        String url = "jdbc:hsqldb:file:db/lesha-master";
        String user = "sa";
        String password = "";
        String driver = "org.hsqldb.jdbcDriver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
