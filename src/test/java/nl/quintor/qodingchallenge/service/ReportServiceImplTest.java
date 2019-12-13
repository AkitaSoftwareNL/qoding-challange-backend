package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.persistence.dao.ReportDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class ReportServiceImplTest {

    private final int campaignId = 1;
    private final int participantId = 1;
    private final String campaignName = "Principal U.S. Small Cap Index ETF";

    private ReportDAO reportDAOMock;
    private ParticipantDAO participantDAOMock;
    private CampaignDAO campaignDAOMock;

    private ReportServiceImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new ReportServiceImpl();

        this.campaignDAOMock = mock(CampaignDAO.class);
        this.participantDAOMock = mock(ParticipantDAO.class);
        this.reportDAOMock = mock(ReportDAO.class);

        this.sut.setCampaignDAO(campaignDAOMock);
        this.sut.setParticipantDAO(participantDAOMock);
        this.sut.setReportDAO(reportDAOMock);
    }

    @Test
    void getAllCampaignsCallsCampaignDAOGetAllCampaigns() throws SQLException {
        sut.getAllCampaigns();

        verify(campaignDAOMock).getAllCampaigns();
    }


    @Test
    void getAllCampaingsReturnsListWithCampaigns() throws SQLException {
        when(campaignDAOMock.getAllCampaigns()).thenReturn(getListCampaign());

        assertEquals(sut.getAllCampaigns(), campaignDAOMock.getAllCampaigns());
    }

    @Test
    void getRankedParticipantsPerCampaignCallsCampaignDAOGetCampaignName() throws SQLException {
        sut.getRankedParticipantsPerCampaign(campaignId);

        verify(campaignDAOMock).getCampaignName(campaignId);
    }


    @Test
    void getRankedParticipantsPerCampaignCallsReportDAOGetRankedParticipantsPerCampaign() throws SQLException {
        sut.getRankedParticipantsPerCampaign(campaignId);

        verify(reportDAOMock).getRankedParticipantsPerCampaign(campaignId);
    }

    @Test
    void getRankedParticipantPerCampaignReturnsRankedParticipantCollection() throws SQLException {
        // Mock
        when(campaignDAOMock.getCampaignName(campaignId)).thenReturn(campaignName);
        when(reportDAOMock.getRankedParticipantsPerCampaign(campaignId)).thenReturn(getRankedParticipants());
        // Test
        var testValue = sut.getRankedParticipantsPerCampaign(campaignId);
        // Verify
        assertEquals(getRankedparticipantCollection(), testValue);
    }

    @Test
    void getAnswerPerParticipantCallsGetFirstAndLastName() throws SQLException {
        setupGetAnswerPerParticipant();

        verify(participantDAOMock).getFirstAndLastname(participantId);
    }

    @Test
    void getAnswerPerParticipantCallsGetAnswerPerParticipant() throws SQLException {
        setupGetAnswerPerParticipant();

        verify(reportDAOMock).getAnswersPerParticipant(campaignId, participantId);
    }

    @Test
    void getAnswerPerParticipantReturnsAnswerCollection() throws SQLException {
        when(participantDAOMock.getFirstAndLastname(participantId)).thenReturn(getAnswerCollection());
        when(campaignDAOMock.getCampaignName(campaignId)).thenReturn(campaignName);
        when(reportDAOMock.getAnswersPerParticipant(campaignId, participantId)).thenReturn(getListAnswer());

        var testValue = sut.getAnswersPerParticipant(campaignId, participantId);

        assertEquals(getAnswerCollection(), testValue);
    }

    private void setupGetAnswerPerParticipant() throws SQLException {
        when(participantDAOMock.getFirstAndLastname(participantId)).thenReturn(getAnswerCollection());

        sut.getAnswersPerParticipant(campaignId, participantId);
    }

    private CampaignDTO getCampaignDTO() {
        return new CampaignDTO(campaignId, campaignName, "me", "JAVA", 3, "12/2/2019", 1, null);
    }

    private List<CampaignDTO> getListCampaign() {
        List<CampaignDTO> campaigns = new ArrayList<>();
        campaigns.add(getCampaignDTO());
        return campaigns;
    }

    private AnswerDTO getAnswerDTO() {
        return new AnswerDTO("A", "A or B?", 1, "multiple");
    }

    private List<AnswerDTO> getListAnswer() {
        List<AnswerDTO> answers = new ArrayList<>();
        answers.add(getAnswerDTO());
        return answers;
    }

    private AnswerCollection getAnswerCollection() {
        return new AnswerCollection("Name", "", "HC2 Holdings, Inc", campaignName, campaignId, getListAnswer());
    }

    private List<ParticipantDTO> getRankedParticipants() {
        List<ParticipantDTO> list = new ArrayList<>();
        list.add(new ParticipantDTO(1, campaignId, 290, "Name", "", "lastName", "mail", "telefoonnummer", 1));
        return list;
    }

    private RankedParticipantCollection getRankedparticipantCollection() {
        return new RankedParticipantCollection(campaignName, getRankedParticipants());
    }
}