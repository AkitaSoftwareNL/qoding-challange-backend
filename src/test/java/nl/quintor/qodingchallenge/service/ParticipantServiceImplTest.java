package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAOImpl;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAOImpl;
import nl.quintor.qodingchallenge.service.exception.CampaignDoesNotExistsException;
import nl.quintor.qodingchallenge.service.exception.CouldNotAddParticipantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ParticipantServiceImplTest {

    private final int campaignId = 1;
    private ParticipantServiceImpl sut;
    private ParticipantDAOImpl participantDAOMock;

    @BeforeEach
    void setUp() {
        this.sut = new ParticipantServiceImpl();

        this.participantDAOMock = mock(ParticipantDAOImpl.class);

        this.sut.setParticipantDAO(participantDAOMock);
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignCallsParticipantAlreadyExists() throws SQLException {
        sut.addParticipant(getParticipantDTO().getCampaignID(), getParticipantDTO());

        verify(participantDAOMock).participantHasAlreadyParticipatedInCampaign(getParticipantDTO(), campaignId);
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignThrowsCouldNotAddParticipantException() throws SQLException {
        when(participantDAOMock.participantHasAlreadyParticipatedInCampaign(getParticipantDTO(), campaignId)).thenReturn(true);
        assertThrows(CouldNotAddParticipantException.class, () -> sut.addParticipant(getParticipantDTO().getCampaignID(), getParticipantDTO()));
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignCallsParticipantHasAlreadyParticipatedInCampaignParticipantDAO() throws SQLException {
        sut.addParticipant(getParticipantDTO().getCampaignID(), getParticipantDTO());
        verify(participantDAOMock).addParticipant(getParticipantDTO(), campaignId);
    }

    private ParticipantDTO getParticipantDTO() throws SQLException {
        return new ParticipantDTOBuilder().with(participantDTOBuilder -> {
                    participantDTOBuilder.firstname = "name";
                    participantDTOBuilder.lastname = "name";
                    participantDTOBuilder.participantID = "1";
                    participantDTOBuilder.campaignID = 1;
                    participantDTOBuilder.timeInMillis = 10000;
                    participantDTOBuilder.email = "name@gmail.com";
                    participantDTOBuilder.phonenumber = "0693873";
                }
        ).build();
    }
}