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
        sut.addParticipant(getParticipantDTO(), campaignID);

        assertEquals(amountOfParticipantsBeforeInsert + 1, sut.getParticipantsPerCampaign(1).size());
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignReturnsTrueWhenParticipantExists() throws SQLException {
        assertTrue(sut.participantHasAlreadyParticipatedInCampaign(getExistingParticipant(), campaignID));
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignReturnsFalseWhenParticipantDoesNotExists() throws SQLException {
        assertFalse(sut.participantHasAlreadyParticipatedInCampaign(getParticipantDTO(), campaignID));
    }

    private ParticipantDTO getExistingParticipant() {
        final int CAMPAIGN_ID = 1;
        return new ParticipantDTO.Builder("Gray", "Snare")
                .id(1)
                .participatedCampaignID(CAMPAIGN_ID)
                .timeOf(100000)
                .insertion(null)
                .email("gsnare0@xinhuanet.com")
                .phonenumber("2219773471")
                .build();
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO.Builder("name", "name")
                .id(20)
                .participatedCampaignID(1)
                .timeOf(100000)
                .insertion(null)
                .email("name@gmail.com")
                .phonenumber("062083423")
                .build();
    }
}