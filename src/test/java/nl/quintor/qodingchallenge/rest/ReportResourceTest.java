package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportResourceTest {

    private final int CAMPAIGNID = 5;
    private final String JFALL = "JFALL";

    private ReportService reportServiceMock;
    private ReportResource sut;

    @BeforeEach
    void setUp() {
        this.sut = new ReportResource();
        this.reportServiceMock = mock(ReportService.class);
        this.sut.setReportService(reportServiceMock);
    }

    @Test
    void getAllCampaignsReturnsAllCampaingsWithStatuscodeOk() throws SQLException {
        when(reportServiceMock.getAllCampaings()).thenReturn(getListCampaign());

        ResponseEntity<List<CampaignDTO>> result = sut.getAllCampaings();

        assertTrue(result.hasBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getRankedParticipantsPerCampaignReturnsRankedListWithParticipantsWithStatuscodeOk() throws SQLException {
        when(reportServiceMock.getRankedParticipantsPerCampaign(CAMPAIGNID)).thenReturn(getRankedListOfParticipants());

        ResponseEntity<RankedParticipantCollection> result = sut.getRankedParticipantsPerCampaign(CAMPAIGNID);

        assertTrue(result.hasBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAnswersPerParticipantReturnsAnswerCollectionAndStatuscodeOK() throws SQLException {
        final int PARTICIPANT_ID = 2;
        when(reportServiceMock.getAnswersPerParticipant(CAMPAIGNID, PARTICIPANT_ID)).thenReturn(getAnswerCollection());

        ResponseEntity<AnswerCollection> result = sut.getAnswersPerParticipant(CAMPAIGNID, PARTICIPANT_ID);

        assertTrue(result.hasBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private CampaignDTO getCampaignDTO() {
        return new CampaignDTO(JFALL, 3, "me", "JAVA", null);
    }

    private List<CampaignDTO> getListCampaign() {
        List<CampaignDTO> campaigns = new ArrayList<>();
        campaigns.add(getCampaignDTO());
        return campaigns;
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO(1, 2, new Time(300), "jan", "van", "peter", "ik@gmail.com", "069839428", 4);
    }

    private List<ParticipantDTO> getListParticipant() {
        List<ParticipantDTO> participants = new ArrayList<>();
        participants.add(getParticipantDTO());
        return participants;
    }

    private RankedParticipantCollection getRankedListOfParticipants() {
        return new RankedParticipantCollection(JFALL, getListParticipant());
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