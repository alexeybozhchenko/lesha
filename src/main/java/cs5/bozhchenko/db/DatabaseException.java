package cs5.bozhchenko.db;

import java.sql.SQLException;

/**
 * Created by motorcrue on 29.10.2017.
 */
public class DatabaseException extends Exception {
    public DatabaseException(SQLException e) {
        super(e);
    }
    public DatabaseException(String string) {
        super(string);
    }
}