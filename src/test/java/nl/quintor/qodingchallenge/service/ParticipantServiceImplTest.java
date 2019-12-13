package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        final int campaignID = 1;
        final int participantID = 1;

        sut.addParticipantToCampaign(campaignID, participantID);

        verify(participantDAOMock).addParticipantToCampaign(campaignID, participantID);
    }
}