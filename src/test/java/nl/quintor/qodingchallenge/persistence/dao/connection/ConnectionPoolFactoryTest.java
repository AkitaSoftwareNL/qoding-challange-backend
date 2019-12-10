package nl.quintor.qodingchallenge.persistence.dao.connection;

import nl.quintor.qodingchallenge.persistence.connection.ConnectionFactoryPoolWrapper;
import nl.quintor.qodingchallenge.persistence.connection.IConnectionFactoryPoolWrapper;
import nl.quintor.qodingchallenge.persistence.exception.PropertiesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

class ConnectionPoolFactoryTest {

    private IConnectionFactoryPoolWrapper sut;

    @BeforeEach
    void setUp() {
        sut = spy(ConnectionFactoryPoolWrapper.class);
    }

    @Test
    void propertiesNotFoundExceptionReturnsRightMessage() throws SQLException {
        given(sut.getConnection()).willAnswer(invocationOnMock -> {
                    throw new PropertiesNotFoundException("a message", new Throwable()
                    );
                }
        );
        assertThrows(PropertiesNotFoundException.class, () -> sut.getConnection());
    }
}
