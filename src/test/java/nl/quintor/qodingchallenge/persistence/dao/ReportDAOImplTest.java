package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerDTO;
import nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactoryWrapper;
import nl.quintor.qodingchallenge.persistence.connection.IConnectionPoolFactoryWrapper;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ReportDAOImplTest {

    private final String participantId = "1";
    private final int campaignId = 1;

    private ReportDAOImpl sut;

    private List<AnswerDTO> answers = spy(new ArrayList<>());
    private IConnectionPoolFactoryWrapper connectionPoolFactoryWrapper;

    @BeforeEach
    void setUp() throws SQLException {
        this.sut = new ReportDAOImpl();

        connectionPoolFactoryWrapper = spy(ConnectionPoolFactoryWrapper.class);

        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testReportDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        answers.add(sut.getAnswersPerParticipant(campaignId, participantId).get(0));
    }

    @Test
    void getAnswersPerParticipantsReturnListWithAnswers() throws SQLException {
        int sizeOfAnswers = 3;

        when(answers.size()).thenReturn(sizeOfAnswers);

        assertEquals(answers.size(), sut.getAnswersPerParticipant(campaignId, participantId).size());
    }

    @Test
    void getAnswersPerParticipantReturnsListWithAnAnswer() throws SQLException {
        assertEquals(answers.get(0), sut.getAnswersPerParticipant(campaignId, participantId).get(0));
    }
}