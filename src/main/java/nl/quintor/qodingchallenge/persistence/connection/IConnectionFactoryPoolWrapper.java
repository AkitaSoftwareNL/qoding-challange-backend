package nl.quintor.qodingchallenge.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionFactoryPoolWrapper {
    Connection getConnection() throws SQLException;
}
