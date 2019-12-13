package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAOImpl;
import nl.quintor.qodingchallenge.service.exception.CouldNotAddParticipantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ParticipantServiceImplTest {

    private ParticipantServiceImpl sut;
    private ParticipantDAOImpl participantDAOMock;

    @BeforeEach
    void setUp() {
        this.sut = new ParticipantServiceImpl();

        this.participantDAOMock = mock(ParticipantDAOImpl.class);

        this.sut.setParticipantDAO(participantDAOMock);
    }

    @Test
    void addParticipantToCampaignCallsParticipantDAO() throws SQLException {
        sut.addParticipantToCampaign(getParticipantDTO().getCampaignID(), getParticipantDTO().getParticipantID());

        verify(participantDAOMock).addParticipantToCampaign(getParticipantDTO().getCampaignID(), getParticipantDTO().getParticipantID());
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignCallsParticipantAlreadyExists() throws SQLException {
        sut.participantHasAlreadyParticipatedInCampaign(getParticipantDTO().getCampaignID(), getParticipantDTO());

        verify(participantDAOMock).participantHasAlreadyParticipatedInCampaign(getParticipantDTO());
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignThrowsCouldNotAddParticipantException() throws SQLException {
        when(participantDAOMock.participantHasAlreadyParticipatedInCampaign(getParticipantDTO())).thenReturn(true);
        assertThrows(CouldNotAddParticipantException.class, () -> sut.participantHasAlreadyParticipatedInCampaign(getParticipantDTO().getCampaignID(), getParticipantDTO()));
    }

    @Test
    void participantHasAlreadyParticipatedInCampaignCallsParticipantHasAlreadyParticipatedInCampaignParticipantDAO() throws SQLException {
        sut.participantHasAlreadyParticipatedInCampaign(getParticipantDTO().getCampaignID(), getParticipantDTO());

        verify(participantDAOMock).addParticipant(getParticipantDTO());
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO(1, 1, 100000, "name", null, "name", "name@gmail.com", "06923934");
    }
}