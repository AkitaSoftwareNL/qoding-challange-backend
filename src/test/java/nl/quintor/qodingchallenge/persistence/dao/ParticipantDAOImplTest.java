package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

class ParticipantDAOImplTest {

    private ParticipantDAOImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new ParticipantDAOImpl();

        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testParticipantDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getFirstAndLastNameReturnsAnAnswerCollectionWithoutAnswers() throws SQLException {
        Assertions.assertEquals(new AnswerCollection("Gray", "Snare", null), sut.getFirstAndLastname(1));
    }
}