package nl.quintor.qodingchallenge.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolFactoryWrapper implements IConnectionPoolFactoryWrapper {

    @Override
    public Connection getConnection() throws SQLException {
        return ConnectionPoolFactory.getConnection();
    }
}
