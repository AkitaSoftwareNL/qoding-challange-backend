package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ParticipantResource {

    private ParticipantService participantService;

    @Autowired
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @ResponseBody
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/login/{campaignID}",
            method = RequestMethod.POST
    )
    public ResponseEntity login(@PathVariable int campaignID, @RequestBody ParticipantDTO participantDTO) throws SQLException {
        participantService.addParticipant(campaignID, participantDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
