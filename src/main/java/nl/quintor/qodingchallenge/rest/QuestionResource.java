package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.service.QuestionService;
import nl.quintor.qodingchallenge.service.QuestionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionResource {

    private QuestionService questionService = new QuestionServiceImpl();

    @PostMapping("/{campaignName}/{participantID/{questionID}")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity answerQuestion(@RequestParam GivenAnswerDTO givenAnswer) {

        questionService.validateAnswer(givenAnswer);

        return new ResponseEntity(HttpStatus.OK);
    }
}
