package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.service.ParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ParticipantResourceTest {

    private ParticipantResourceImpl sut;
    private ParticipantServiceImpl participantServiceMock;

    @BeforeEach
    void setUp() {
        this.sut = new ParticipantResourceImpl();

        this.participantServiceMock = mock(ParticipantServiceImpl.class);
        this.sut.setParticipantService(participantServiceMock);
    }

    @Test
    void loginCallsAddParticipant() throws SQLException {
        sut.login(getParticipantDTO().getCampaignID(), getParticipantDTO());

        verify(participantServiceMock).addParticipant(getParticipantDTO().getCampaignID(), getParticipantDTO());
    }

    @Test
    void loginReturnsCorrectResponseCreated() throws SQLException {
        var response = sut.login(getParticipantDTO().getCampaignID(), getParticipantDTO());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO(1, 1, 100000, "name", null, "name", "name@gmail.com", "06923934");
    }
}