package nl.quintor.qodingchallenge.rest;


import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class QuestionResource {

    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
            path = {"/campaign/{campaignName}"},
            method = {RequestMethod.GET})
    public ResponseEntity<QuestionCollection> sendQuestions(@PathVariable String campaignName) throws SQLException {
        QuestionCollection questionCollection = new QuestionCollection(1, campaignName, questionService.getQuestions("java", 3));
        return ResponseEntity.ok().body(
                questionCollection
        );
    }

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            path = {"/campaign/{campaignName}"},
            method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity getAnswer(@RequestBody QuestionCollection questionCollection) throws SQLException {
        questionService.setAnswer(questionCollection);
        return new ResponseEntity(HttpStatus.OK);
    }
}
