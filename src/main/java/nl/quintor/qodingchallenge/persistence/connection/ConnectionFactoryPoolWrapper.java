package nl.quintor.qodingchallenge.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryPoolWrapper {
    public static final ConnectionFactoryPoolWrapper wrapper = new ConnectionFactoryPoolWrapper();

    public Connection getConnection() throws SQLException {
        return ConnectionPoolFactory.getConnection();
    }
}
