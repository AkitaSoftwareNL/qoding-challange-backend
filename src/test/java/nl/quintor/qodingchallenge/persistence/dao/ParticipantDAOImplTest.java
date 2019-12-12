package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;

class ParticipantDAOImplTest {

    private ParticipantDAOImpl sut;

    private final int campaignID = 1;

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
        assertEquals(new AnswerCollection("Gray", null, "Snare", null, 0, null), sut.getFirstAndLastname(1));
    }

    @Test
    void addParticipantToCampaignAddsParticipantToCampaign() throws SQLException {
        final int participantID = 15;

        int participantLengthBeforeAdd = sut.getParticipantsPerCampaign(1).size();

        sut.addParticipantToCampaign(campaignID, participantID);

        assertEquals(participantLengthBeforeAdd + 1, sut.getParticipantsPerCampaign(1).size());
    }

    @Test
    void getParticipantPerCampaignGetsAllParticipantsPerCampaign() throws SQLException {
        final int amountOfParticipants = 4;

        assertEquals(amountOfParticipants, sut.getParticipantsPerCampaign(campaignID).size());
    }

    @Test
    void getParticipantsReturnsListWithParticipants() throws SQLException {
        List<ParticipantDTO> participants = sut.getParticipantsPerCampaign(campaignID);
        assertFalse(participants.isEmpty());
    }
}