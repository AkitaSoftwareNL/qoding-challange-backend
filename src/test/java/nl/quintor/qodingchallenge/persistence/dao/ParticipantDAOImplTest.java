package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.RankedParticipantCollection;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
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
import java.util.Optional;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ParticipantDAOImplTest {

    private final int campaignID = 1;
    private List<ParticipantDTO> participants = spy(new ArrayList<>());

    private ParticipantDAOImpl sut;

    @BeforeEach
    void setUp() throws SQLException {
        this.sut = new ParticipantDAOImpl();

        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
            inputStream = getClass().getClassLoader().getResourceAsStream("DLL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        participants.add(sut.getRankedParticipantsPerCampaign(campaignID).get(0));
    }

    @Test
    void getRankedParticipantsReturnsListWithParticipants() throws SQLException {
        int sizeOfParticipants = 4;

        when(participants.size()).thenReturn(sizeOfParticipants);

        assertEquals(participants.size(), sut.getRankedParticipantsPerCampaign(campaignID).size());
    }

    @Test
    void getRankedParticipantReturnsListWithAnParticipant() throws SQLException {
        assertEquals(participants.get(0), sut.getRankedParticipantsPerCampaign(campaignID).get(0));
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
                assertEquals(min[0], sut.getRankedParticipantsPerCampaign(campaignID).get(0).getTimeInMillis());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void getFirstAndLastNameReturnsAnAnswerCollectionWithoutAnswers() throws SQLException {
        assertEquals(new AnswerCollection("Gray", null, "Snare", null, 0, null), sut.getFirstAndLastname("1"));
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignReturnsTrueWhenParticipantExists() throws SQLException {
        assertTrue(sut.participantHasAlreadyParticipatedInCampaign(getExistingParticipant(), campaignID));
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignReturnsFalseWhenParticipantDoesNotExists() throws SQLException {
        assertFalse(sut.participantHasAlreadyParticipatedInCampaign(getParticipantDTO(), campaignID));
    }

    @Test
    void getParticipantsReturnsListWithParticipants() throws SQLException {
        List<ParticipantDTO> participants = sut.getRankedParticipantsPerCampaign(campaignID);
        assertFalse(participants.isEmpty());
    }

    @Test
    void addParticipantAddsAnParticipant() throws SQLException {
        final int amountBeforeInsert = 4;

        sut.addParticipant(getParticipantDTO(), campaignID);

        assertEquals(amountBeforeInsert + 1, sut.getRankedParticipantsPerCampaign(campaignID).size());
    }

    @Test
    void addParticipantCreatesUniqueID() throws SQLException {
        List<ParticipantDTO> participants = sut.getRankedParticipantsPerCampaign(campaignID);

        assertNotEquals(participants.get(0).getParticipantID(), participants.get(1).getParticipantID(), participants.get(2).getParticipantID());
    }

    private ParticipantDTO getExistingParticipant() throws SQLException {
        return new ParticipantDTOBuilder().with(participantDTOBuilder -> {
                    participantDTOBuilder.firstname = "Gray";
                    participantDTOBuilder.lastname = "Snare";
                    participantDTOBuilder.participantID = "1";
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
                    participantDTOBuilder.campaignID = 1;
                    participantDTOBuilder.timeInMillis = 10000;
                    participantDTOBuilder.email = "name@gmail.com";
                    participantDTOBuilder.phonenumber = "0693873";
                }
        ).build();
    }

    private RankedParticipantCollection getRankedParticipantCollection() throws SQLException {
        return new RankedParticipantCollection("JFALL", sut.getRankedParticipantsPerCampaign(campaignID));
    }

    @Test
    void addTimeToParticipantAddsTimeParticipantToCampaign() throws SQLException {
        final String participantWithoutEndTime = "2";
        sut.addTimeToParticipant(participantWithoutEndTime);
        final List<ParticipantDTO> participants = sut.getRankedParticipantsPerCampaign(1);
        final Optional<ParticipantDTO> testValue = participants.stream().filter(participantDTO -> participantDTO.getParticipantID().equals(participantWithoutEndTime)).findAny();


        assertTrue(testValue.orElse(new ParticipantDTO()).getTimeInMillis() != 0);
    }
}