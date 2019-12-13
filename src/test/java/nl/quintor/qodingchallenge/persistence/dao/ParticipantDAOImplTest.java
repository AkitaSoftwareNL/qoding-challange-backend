package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.*;

class ParticipantDAOImplTest {

    private final int campaignID = 1;
    private final int amountOfParticipantsBeforeInsert = 4;
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
        assertEquals(amountOfParticipantsBeforeInsert, sut.getParticipantsPerCampaign(campaignID).size());
    }

    @Test
    void getParticipantsReturnsListWithParticipants() throws SQLException {
        List<ParticipantDTO> participants = sut.getParticipantsPerCampaign(campaignID);
        assertFalse(participants.isEmpty());
    }

    @Test
    void addParticipantAddsAnParticipantToConferenceTable() throws SQLException {
        sut.addParticipant(getParticipantDTO());

        assertEquals(amountOfParticipantsBeforeInsert + 1, sut.getParticipantsPerCampaign(1).size());
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignReturnsTrueWhenParticipantExists() throws SQLException {
        assertTrue(sut.participantHasAlreadyParticipatedInCampaign(getExistingParticipant()));
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignReturnsFalseWhenParticipantDoesNotExists() throws SQLException {
        assertFalse(sut.participantHasAlreadyParticipatedInCampaign(getParticipantDTO()));
    }

    private ParticipantDTO getExistingParticipant() {
        return new ParticipantDTO(1, 1, 10000,"Gray", null, "Snare", "gsnare0@xinhuanet.com", "2219773471");
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO(20, 1, 100000, "name", null, "name", "name@gmail.com", "06923934");
    }
}