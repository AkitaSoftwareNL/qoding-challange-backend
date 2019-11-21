package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.ParticipantService;
import nl.quintor.qodingchallenge.service.ParticipantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ParticipantResource {

    private ParticipantService participantService = new ParticipantServiceImpl();

    @RequestMapping(method = RequestMethod.POST,
            produces = {"application/json"},
            path = "/{participantName")
    public ResponseEntity answerQuestion(@PathVariable String participantName,
                                                      @RequestParam String givenAnswer,
                                                      @RequestParam String questionType) {
        participantService.validateAnswer(participantName, givenAnswer, questionType);

        return new ResponseEntity(HttpStatus.OK);
    }
}
