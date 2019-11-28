package nl.quintor.qodingchallenge.persistence.connection;

import nl.quintor.qodingchallenge.persistence.exception.PropertiesNotFoundException;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class ConnectionPoolFactory {

    private static final String DATABASE_RESOURCE_LINK = "database.properties";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final int MIN_IDLE = 5;
    private static final int MAX_IDLE = 10;
    private static final int MAX_OPEN_PREPARED_STATEMENT = 100;

    private static final BasicDataSource ds = new BasicDataSource();
    private static Properties properties;

    private ConnectionPoolFactory() {
    }

    static {
        try {
            setProperties();
        } catch (PropertiesNotFoundException e) {
            e.printStackTrace();
        }
        ds.setUrl(properties.getProperty(DB_URL));
        ds.setUsername(properties.getProperty(DB_USER));
        ds.setPassword(properties.getProperty(DB_PASSWORD));
        ds.setMinIdle(MIN_IDLE);
        ds.setMaxIdle(MAX_IDLE);
        ds.setMaxOpenPreparedStatements(MAX_OPEN_PREPARED_STATEMENT);
    }

    private static void setProperties() throws PropertiesNotFoundException {
        if (properties == null) {
            properties = readProperties();
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static Properties readProperties() throws PropertiesNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(requireNonNull(ConnectionPoolFactory.class.getClassLoader().getResourceAsStream(DATABASE_RESOURCE_LINK)));
            Class.forName(properties.getProperty(DB_DRIVER));
        } catch (IOException | ClassNotFoundException e) {
            throw new PropertiesNotFoundException("Properties not found. Check " + DATABASE_RESOURCE_LINK, e.getCause());
        }
        return properties;
    }
}