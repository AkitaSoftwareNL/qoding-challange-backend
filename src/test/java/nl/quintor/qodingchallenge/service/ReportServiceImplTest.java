package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.AnswerDTO;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
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

    private final int CAMPAIGNID = 1;
    private final int PARTICIPANT_ID = 1;

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
        sut.getAllCampaings();

        verify(campaignDAOMock).getAllCampaigns();
    }


    @Test
    void getAllCampaingsReturnsListWithCampaigns() throws SQLException {
        when(campaignDAOMock.getAllCampaigns()).thenReturn(getListCampaign());

        assertEquals(sut.getAllCampaings(), campaignDAOMock.getAllCampaigns());
    }

    @Test
    void getRankedParticipantsPerCampaignCallsCampaignDAOGetCampaignName() throws SQLException {
        sut.getRankedParticipantsPerCampaign(CAMPAIGNID);

        verify(campaignDAOMock).getCampaignName(CAMPAIGNID);
    }


    @Test
    void getRankedParticipantsPerCampaignCallsReportDAOGetRankedParticipantsPerCampaign() throws SQLException {
        sut.getRankedParticipantsPerCampaign(CAMPAIGNID);

        verify(reportDAOMock).getRankedParticipantsPerCampaign(CAMPAIGNID);
    }

    @Test
    void getAnswerPerParticipantCallsParticipantDAOGetFirstAndLastName() throws SQLException {
        when(participantDAOMock.getFirstAndLastname(PARTICIPANT_ID)).thenReturn(getAnswerCollection());

        sut.getAnswersPerParticipant(CAMPAIGNID, PARTICIPANT_ID);

        verify(participantDAOMock).getFirstAndLastname(PARTICIPANT_ID);
    }

    @Test
    void getAnswerPerParticipantCallsReportDAOGetAnswerPerParticipant() throws SQLException {
        when(participantDAOMock.getFirstAndLastname(PARTICIPANT_ID)).thenReturn(getAnswerCollection());

        sut.getAnswersPerParticipant(CAMPAIGNID, PARTICIPANT_ID);

        verify(reportDAOMock).getAnswersPerParticipant(CAMPAIGNID, PARTICIPANT_ID);
    }

    private CampaignDTO getCampaignDTO() {
        return new CampaignDTO(1, "JFALL","me","JAVA", 3, "12/2/2019", 1, null);
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
        return new AnswerCollection("Name", "anothername", getListAnswer());
    }
}