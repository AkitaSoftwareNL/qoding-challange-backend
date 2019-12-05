package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerDTO;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.RankedParticipantCollection;
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

    private final int PARTICIPANT_ID = 1;
    private final int CAMPAIGN_ID = 1;
    private ReportDAOImpl sut;

    private List<ParticipantDTO> participants = spy(new ArrayList<>());
    private List<AnswerDTO> answers = spy(new ArrayList<>());

    @BeforeEach
    void setUp() throws SQLException {
        this.sut = new ReportDAOImpl();

        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testReportDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        answers.add(sut.getAnswersPerParticipant(CAMPAIGN_ID, PARTICIPANT_ID).get(0));
        participants.add(sut.getRankedParticipantsPerCampaign(CAMPAIGN_ID).get(0));
    }

    @Test
    void getRankedParticipantsReturnsListWithParticipants() throws SQLException {
        when(participants.size()).thenReturn(3);

        assertEquals(participants.size(), sut.getRankedParticipantsPerCampaign(CAMPAIGN_ID).size());
    }

    @Test
    void getRankedParticipantReturnsListWithAnParticipant() throws SQLException {
        assertEquals(participants.get(0), sut.getRankedParticipantsPerCampaign(CAMPAIGN_ID).get(0));
    }

    @Test
    void getRankedParticipantReturnsOrderedParticipantList() throws SQLException {
        final long[] min = {0};
        getRankedParticipantCollection().getParticipants().iterator().forEachRemaining(participantDTO -> {
            if (min[0] == 0) min[0] = participantDTO.getTimeInMillis();
            if (min[0] > participantDTO.getTimeInMillis()) {
                min[0] = participantDTO.getTimeInMillis();
            }
            try {
                assertEquals(min[0], sut.getRankedParticipantsPerCampaign(CAMPAIGN_ID).get(0).getTimeInMillis());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void getAnswersPerParticipantsReturnListWithAnswers() throws SQLException {
        when(answers.size()).thenReturn(3);

        assertEquals(answers.size(), sut.getAnswersPerParticipant(CAMPAIGN_ID, PARTICIPANT_ID).size());
    }

    @Test
    void getAnswersPerParticipantReturnsListWithAnAnswer() throws SQLException {
        assertEquals(answers.get(0), sut.getAnswersPerParticipant(CAMPAIGN_ID, PARTICIPANT_ID).get(0));
    }

    private RankedParticipantCollection getRankedParticipantCollection() throws SQLException {
        return new RankedParticipantCollection("JFALL", sut.getRankedParticipantsPerCampaign(CAMPAIGN_ID));
    }
}