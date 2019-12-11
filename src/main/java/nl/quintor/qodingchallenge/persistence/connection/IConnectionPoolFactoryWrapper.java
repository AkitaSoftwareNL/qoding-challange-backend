package nl.quintor.qodingchallenge.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPoolFactoryWrapper {
    Connection getConnection() throws SQLException;
}
