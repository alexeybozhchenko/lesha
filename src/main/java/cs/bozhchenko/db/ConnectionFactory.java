package cs.bozhchenko.db;

import java.sql.Connection;

/**
 * Created by motorcrue on 29.10.2017.
 */
public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;

}
