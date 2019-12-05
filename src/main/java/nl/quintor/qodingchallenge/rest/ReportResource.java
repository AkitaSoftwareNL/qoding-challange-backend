package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

public class ReportResource {

    private ReportService reportService;

    @RequestMapping(path = "/report",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CampaignDTO>> getAllCampaings() throws SQLException {
        return new ResponseEntity<>(reportService.getAllCampaings(), HttpStatus.OK);
    }

    @RequestMapping(path = "/report/{campaignID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankedParticipantCollection> getRankedParticipantsPerCampaign(@PathVariable int campaignID) throws SQLException {
        return new ResponseEntity<>(reportService.getRankedParticipantsPerCampaign(campaignID) ,HttpStatus.OK);
    }

    @RequestMapping(path = "/report/{campaingID}/{participantID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerCollection> getAnswersPerParticipant(@PathVariable int campaignID, @PathVariable int participantID) throws SQLException {
        return new ResponseEntity<>(reportService.getAnswersPerParticipant(campaignID, participantID) ,HttpStatus.OK);
    }
}
