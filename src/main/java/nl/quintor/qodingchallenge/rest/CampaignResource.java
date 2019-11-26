package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.exception.CampaignAlreadyExistsException;
import nl.quintor.qodingchallenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class CampaignResource {


    private CampaignService campaignService;

    @Autowired
    public void setCampaignService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @ResponseBody
    @RequestMapping(path = "/campaign/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CampaignDTO>> createCampaign(@RequestBody CampaignDTO campaignDTO) throws SQLException {
        return new ResponseEntity<>(campaignService.createNewCampaign(campaignDTO.getName()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(path = "/campaign",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CampaignDTO>> showCampaign() throws SQLException {
        return new ResponseEntity<>(campaignService.showCampaign(), HttpStatus.OK);
    }
}
