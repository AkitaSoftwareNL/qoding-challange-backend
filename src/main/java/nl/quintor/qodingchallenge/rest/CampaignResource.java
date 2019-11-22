package nl.quintor.qodingchallenge.rest;

import nl.quintor.dto.CampaignDTO;
import nl.quintor.qodingchallenge.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping(path = "/campaign")
public class CampaignResource {

    private CampaignService campaignService = new CampaignService();

    @ResponseBody
    @RequestMapping(path = "/create",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<java.util.List<CampaignDTO>> createCampaign(@RequestBody CampaignDTO campaignDTO) throws SQLException {
        return new ResponseEntity<>(campaignService.createNewCampaign(campaignDTO.getName()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<java.util.List<CampaignDTO>> showCampaign() throws SQLException {
        return new ResponseEntity<>(campaignService.showCampaign(), HttpStatus.OK);
    }
}
