package nl.quintor.qodingchallenge.rest;


import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
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

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignName}",
            method = RequestMethod.GET)
    public ResponseEntity<QuestionCollection> sendQuestions(@PathVariable String campaignName) throws SQLException {
        List<QuestionDTO> questions = questionService.getQuestions("java", campaignName);
        QuestionCollection questionCollection = new QuestionCollection(1, campaignName, questions);
        return ResponseEntity.ok().body(questionCollection);
    }

    @ResponseBody
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignName}",
            method = RequestMethod.POST)
    public ResponseEntity getAnswer(@RequestBody QuestionCollection questionCollection) throws SQLException {
        questionService.setAnswer(questionCollection);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignid}/answers/{state}",
            method = RequestMethod.GET)
    public ResponseEntity getPendingAnswers(@PathVariable("campaignid") int campaignId, @PathVariable("state") int questionState) throws SQLException {
        return ResponseEntity.ok().body(questionService.getPendingAnswers(campaignId, questionState));
    }

    @ResponseBody
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/answers/update",
            method = RequestMethod.POST)
    public ResponseEntity setPendingAnswer(@RequestBody GivenAnswerDTO givenAnswerDTO) throws SQLException {
        questionService.setPendingAnswer(givenAnswerDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/questions/{questionid}",
            method = RequestMethod.GET)
    public ResponseEntity getQuestion(@PathVariable("questionid") int questionId) throws SQLException, NoQuestionFoundException {
        return ResponseEntity.ok().body(questionService.getQuestion(questionId));
    }

    @ResponseBody
    @RequestMapping(path = "/questions/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createQuestion(QuestionDTO question) throws SQLException {
        questionService.createQuestion(question);
        return ResponseEntity.status(200).build();
    }

    @ResponseBody
    @RequestMapping(path = "/questions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionCollection> getAllQuestions() throws SQLException {
        QuestionCollection questionCollection = new QuestionCollection();
        questionCollection.setQuestions(questionService.getAllQuestions());
        return ResponseEntity.ok().body(questionCollection);
    }
}
