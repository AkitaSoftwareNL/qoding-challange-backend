package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTOCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class QuestionResource {

    private IQuestionService questionService;

    @Autowired
    public void setQuestionService(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/pleasework")
    // TODO: 11/21/2019 Fix parameters of get question away from the default questions
    public ResponseEntity<List<QuestionDTO>> sendQuestions() throws SQLException {
        return ResponseEntity.ok().body(questionService.getQuestions("java", 3));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/helpme")
    public ResponseEntity getAnswer(@RequestParam GivenAnswerDTOCollection givenAnswerDTOCollection) throws SQLException {
        questionService.setAnswer(givenAnswerDTOCollection);
        return new ResponseEntity(HttpStatus.OK);
    }
}
