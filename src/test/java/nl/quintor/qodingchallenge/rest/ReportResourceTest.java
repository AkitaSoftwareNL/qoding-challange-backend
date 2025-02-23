package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
import nl.quintor.qodingchallenge.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class ReportResourceTest {

    private final int campaignId = 5;
    private final String campaign = "campaign";
    private final String participantId = "2";

    private ReportService reportServiceMock;
    private ReportResource sut;

    @BeforeEach
    void setUp() {
        this.sut = new ReportResource();
        this.reportServiceMock = mock(ReportService.class);
        this.sut.setReportService(reportServiceMock);
    }

    @Test
    void getAllCampaignsCallsGetAllCampaigns() throws SQLException {
        // Mock

        // Test
        sut.getAllCampaigns();
        // Verify
        verify(reportServiceMock).getAllCampaigns();
    }

    @Test
    void getAllCampaignsReturnsAllCampaignsWithStatuscodeOk() throws SQLException {
        when(reportServiceMock.getAllCampaigns()).thenReturn(getListCampaign());

        ResponseEntity<List<CampaignDTO>> result = sut.getAllCampaigns();

        assertEquals(getListCampaign(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getRankedParticipantsPerCampaignCallsGetRankedParticipantsPerCampaign() throws SQLException {
        // Mock

        // Test
        sut.getRankedParticipantsPerCampaign(campaignId);
        // Verify
        verify(reportServiceMock).getRankedParticipantsPerCampaign(campaignId);
    }

    @Test
    void getRankedParticipantsPerCampaignReturnsRankedListWithParticipantsWithStatuscodeOk() throws SQLException {
        when(reportServiceMock.getRankedParticipantsPerCampaign(campaignId)).thenReturn(getRankedParticipantCollection());

        ResponseEntity<RankedParticipantCollection> result = sut.getRankedParticipantsPerCampaign(campaignId);

        assertEquals(getRankedParticipantCollection(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAnswersPerParticipantCallsGetAnswersPerParticipant() throws SQLException {
        // Mock

        // Test
        sut.getAnswersPerParticipant(campaignId, participantId);
        // Verify
        verify(reportServiceMock).getAnswersPerParticipant(campaignId, participantId);
    }

    @Test
    void getAnswersPerParticipantReturnsAnswerCollectionAndStatuscodeOK() throws SQLException {
        when(reportServiceMock.getAnswersPerParticipant(campaignId, participantId)).thenReturn(getAnswerCollection());

        ResponseEntity<AnswerCollection> result = sut.getAnswersPerParticipant(campaignId, participantId);

        assertEquals(getAnswerCollection(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private CampaignDTO getCampaignDTO() {
        var temp = new ArrayList<AmountOfQuestionTypeDTO>();
        temp.add(new AmountOfQuestionTypeDTO("open", 1));
        return new CampaignDTO(1, campaign, "me", "JAVA", new AmountOfQuestionTypeCollection(temp), "12/2/2019", 1, null);
    }

    private List<CampaignDTO> getListCampaign() {
        List<CampaignDTO> campaigns = new ArrayList<>();
        campaigns.add(getCampaignDTO());
        return campaigns;
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

    private List<ParticipantDTO> getListParticipant() throws SQLException {
        List<ParticipantDTO> participants = new ArrayList<>();
        participants.add(getParticipantDTO());
        return participants;
    }

    private RankedParticipantCollection getRankedParticipantCollection() throws SQLException {
        return new RankedParticipantCollection(campaign, getListParticipant());
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
        return new AnswerCollection("Name", "", "anothername", "campaign", 1, getListAnswer());
    }


}