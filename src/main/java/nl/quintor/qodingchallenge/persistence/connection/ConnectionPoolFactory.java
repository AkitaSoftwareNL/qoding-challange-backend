package nl.quintor.qodingchallenge.persistence.connection;

import nl.quintor.qodingchallenge.persistence.exception.PropertiesNotFoundException;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * <p>Maintains the connection with the database using a pool of connection.
 * When a database.properties is given in the resource map it will use it automatically.<br>
 * <p>
 * The database.properties requires the following tags:
 * <ul>
 *     <li>user</li>
 *     <li>password</li>
 *     <li>driver</li>
 *     <li>url</li>
 * </ul>
 */
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolFactory.class);
    private static Properties properties;

    static {
        try {
            setProperties();
        } catch (PropertiesNotFoundException e) {
            LOGGER.error("Properties could not be read \n", e);
        }
        ds.setUrl(properties.getProperty(DB_URL));
        ds.setUsername(properties.getProperty(DB_USER));
        ds.setPassword(properties.getProperty(DB_PASSWORD));
        ds.setMinIdle(MIN_IDLE);
        ds.setMaxIdle(MAX_IDLE);
        ds.setMaxOpenPreparedStatements(MAX_OPEN_PREPARED_STATEMENT);
    }

    private ConnectionPoolFactory() {
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
            LOGGER.error("An exception has occured \n", e);
            throw new PropertiesNotFoundException("Properties not found. Check " + DATABASE_RESOURCE_LINK, e);
        }
        return properties;
    }
}