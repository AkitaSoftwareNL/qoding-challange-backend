package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.dto.RankedParticipantCollection;
import nl.quintor.qodingchallenge.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ReportResource {

    private ReportService reportService;

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(path = "/report",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns() throws SQLException {
        return new ResponseEntity<>(reportService.getAllCampaigns(), HttpStatus.OK);
    }

    @RequestMapping(path = "/report/{campaignID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankedParticipantCollection> getRankedParticipantsPerCampaign(@PathVariable int campaignID) throws SQLException {
        return new ResponseEntity<>(reportService.getRankedParticipantsPerCampaign(campaignID), HttpStatus.OK);
    }

    @RequestMapping(path = "/report/{campaignID}/{participantID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerCollection> getAnswersPerParticipant(@PathVariable int campaignID, @PathVariable String participantID) throws SQLException {
        return new ResponseEntity<>(reportService.getAnswersPerParticipant(campaignID, participantID), HttpStatus.OK);
    }
}
