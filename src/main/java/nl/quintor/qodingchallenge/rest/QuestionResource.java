package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class QuestionResource {

    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/campaign/{campaignName")
    // TODO: 11/21/2019 Fix parameters of get question away from the default questions
    public ResponseEntity<List<QuestionDTO>> sendQuestions() throws SQLException {
        return ResponseEntity.ok().body(questionService.getQuestions("java", 3));
    }

    @PostMapping
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/campaign/{campaignName")
    public ResponseEntity getAnswer(@RequestParam GivenAnswerlistDTO givenAnswerlistDTO) throws SQLException {
        questionService.setAnswer(givenAnswerlistDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
