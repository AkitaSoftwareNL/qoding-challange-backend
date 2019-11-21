package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.service.IQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class QuestionResource {

    private IQuestionService questionService;

    @PostMapping("")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // TODO: 11/21/2019 Fix parameters of get question away from the default questions
    public ResponseEntity sendQuestions() throws SQLException {
        return ResponseEntity.ok().body(questionService.getQuestions("java", 3));
    }

    @GetMapping("")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAnswer(@RequestParam givenAnswerlistDTO givenAnswerlistDTO) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
