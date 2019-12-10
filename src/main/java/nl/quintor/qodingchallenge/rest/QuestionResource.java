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
        return ResponseEntity.ok().body(questionService.getQuestions("java", campaignName));
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
            path = "/campaign/{campaignid}/answers/{state}/update",
            method = RequestMethod.POST)
    public ResponseEntity setPendingAnswer(@PathVariable("campaignid") int campaignId, @PathVariable("state") int questionState, @RequestBody GivenAnswerDTO givenAnswerDTO) throws SQLException {
        questionService.setPendingAnswer(givenAnswerDTO);
        return ResponseEntity.ok().body(questionService.getPendingAnswers(campaignId, questionState));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/questions/{questionid}",
            method = RequestMethod.GET)
    public ResponseEntity getQuestion(@PathVariable("questionid") int questionId) throws SQLException {
        return ResponseEntity.ok().body(questionService.getQuestion(questionId));
    }

    @ResponseBody
    @RequestMapping(path = "/questions/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createQuestion(@RequestBody QuestionDTO question) throws SQLException {
        questionService.createQuestion(question);
        return ResponseEntity.status(200).build();
    }

    @ResponseBody
    @RequestMapping(path = "/questions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() throws SQLException {
        List<QuestionDTO> questionDTOList = questionService.getAllQuestions();
        return ResponseEntity.ok().body(questionDTOList);
    }

    @RequestMapping(path = "/questions/delete/{questionID}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> removeQuestion(@PathVariable("questionID") int questionID) throws SQLException {
        questionService.removeQuestion(questionID);
        return ResponseEntity.ok().body(questionService.getAllQuestions());
    }
}
