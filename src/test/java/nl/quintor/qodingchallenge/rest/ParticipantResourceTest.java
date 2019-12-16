package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
import nl.quintor.qodingchallenge.service.ParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ParticipantResourceTest {

    private ParticipantResource sut;
    private ParticipantServiceImpl participantServiceMock;

    @BeforeEach
    void setUp() {
        this.sut = new ParticipantResource();

        this.participantServiceMock = mock(ParticipantServiceImpl.class);
        this.sut.setParticipantService(participantServiceMock);
    }

    @Test
    void loginCallsParticipantHasAlreadyParticipatedInCampaign() throws SQLException {
        sut.login(getParticipantDTO().getCampaignID(), getParticipantDTO());

        verify(participantServiceMock).participantHasAlreadyParticipatedInCampaign(getParticipantDTO().getCampaignID(), getParticipantDTO());
    }

    @Test
    void loginReturnsCorrectResponseCreated() throws SQLException {
        var response = sut.login(getParticipantDTO().getCampaignID(), getParticipantDTO());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private ParticipantDTO getParticipantDTO() throws SQLException {
        return new ParticipantDTOBuilder().with(participantDTOBuilder -> {
                    participantDTOBuilder.firstname = "name";
                    participantDTOBuilder.lastname = "name";
                    participantDTOBuilder.participantID = 1;
                    participantDTOBuilder.campaignID = 1;
                    participantDTOBuilder.timeInMillis = 10000;
                    participantDTOBuilder.email = "name@gmail.com";
                    participantDTOBuilder.phonenumber = "0693873";
                }
        ).build();
    }
}