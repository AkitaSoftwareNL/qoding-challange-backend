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
    void addParticipantCallsParticipantAlreadyExists() throws SQLException {
        sut.addParticipant(getParticipantDTO().getCampaignID(), getParticipantDTO());

        verify(participantDAOMock).participantAlreadyExists(getParticipantDTO());
    }

    @Test
    void addParticipantThrowsCouldNotAddParticipantException() throws SQLException {
        when(participantDAOMock.participantAlreadyExists(getParticipantDTO())).thenThrow(new CouldNotAddParticipantException(
                "some message",
                "some details",
                "some action"
        ));

        assertThrows(CouldNotAddParticipantException.class, () -> sut.addParticipant(getParticipantDTO().getCampaignID(), getParticipantDTO()));
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO(1, 1, 100000, "name", null, "name", "name@gmail.com", "06923934");
    }
}