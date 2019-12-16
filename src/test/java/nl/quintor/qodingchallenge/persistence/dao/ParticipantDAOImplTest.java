package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
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

    private ParticipantDTO getExistingParticipant() throws SQLException {
        return new ParticipantDTOBuilder().with(participantDTOBuilder -> {
                    participantDTOBuilder.firstname = "Gray";
                    participantDTOBuilder.lastname = "Snare";
                    participantDTOBuilder.participantID = 1;
                    participantDTOBuilder.campaignID = 1;
                    participantDTOBuilder.timeInMillis = 100000;
                    participantDTOBuilder.email = "gsnare0@xinhuanet.com";
                    participantDTOBuilder.phonenumber = "2219773471";
                }
        ).build();
    }


    private ParticipantDTO getParticipantDTO() throws SQLException {
        return new ParticipantDTOBuilder().with(participantDTOBuilder -> {
                    participantDTOBuilder.firstname = "name";
                    participantDTOBuilder.lastname = "name";
                    participantDTOBuilder.participantID = 20;
                    participantDTOBuilder.campaignID = 1;
                    participantDTOBuilder.timeInMillis = 10000;
                    participantDTOBuilder.email = "name@gmail.com";
                    participantDTOBuilder.phonenumber = "0693873";
                }
        ).build();
    }
}