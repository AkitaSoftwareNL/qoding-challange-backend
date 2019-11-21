package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.service.QuestionService;
import nl.quintor.qodingchallenge.service.QuestionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionResource {

    private QuestionService questionService = new QuestionServiceImpl();

    @PostMapping("/{particpantID")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity answerQuestion(@PathVariable int participantID,
                                         @RequestParam String campaignName,
                                         @RequestParam String givenAnswer) {
        questionService.validateAnswer(participantID, campaignName, givenAnswer);

        return new ResponseEntity(HttpStatus.OK);
    }
}
