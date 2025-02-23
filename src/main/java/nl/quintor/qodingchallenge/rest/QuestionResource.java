package nl.quintor.qodingchallenge.rest;


import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
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

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignID}",
            method = RequestMethod.GET)
    public ResponseEntity<QuestionCollection> sendQuestions(@PathVariable int campaignID) {
        return ResponseEntity.ok().body(questionService.getQuestions("java", campaignID));
    }

    @ResponseBody
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignID}",
            method = RequestMethod.POST)
    public ResponseEntity getAnswer(@PathVariable int campaignID, @RequestBody QuestionCollection questionCollection) throws SQLException {
        questionCollection.setCampaignId(campaignID);
        questionService.setAnswer(questionCollection);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignid}/answers/{state}",
            method = RequestMethod.GET)
    public ResponseEntity getPendingAnswers(@PathVariable("campaignid") int campaignId, @PathVariable("state") int questionState) {
        return ResponseEntity.ok().body(questionService.getPendingAnswers(campaignId, questionState));
    }

    @ResponseBody
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "/campaign/{campaignid}/answers/{state}/update",
            method = RequestMethod.POST)
    public ResponseEntity setPendingAnswer(@PathVariable("campaignid") int campaignId, @PathVariable("state") int questionState, @RequestBody GivenAnswerDTO givenAnswerDTO) {
        questionService.setPendingAnswer(givenAnswerDTO);
        return ResponseEntity.ok().body(questionService.getPendingAnswers(campaignId, questionState));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/questions/{questionid}",
            method = RequestMethod.GET)
    public ResponseEntity getQuestion(@PathVariable("questionid") int questionId) {
        return ResponseEntity.ok().body(questionService.getQuestion(questionId));
    }

    @ResponseBody
    @RequestMapping(path = "/questions/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createQuestion(@RequestBody QuestionDTO question) {
        questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseBody
    @RequestMapping(path = "/questions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questionDTOList = questionService.getAllQuestions();
        return ResponseEntity.ok().body(questionDTOList);
    }

    @RequestMapping(path = "/questions/delete/{questionID}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> removeQuestion(@PathVariable("questionID") int questionID) {
        questionService.removeQuestion(questionID);
        return ResponseEntity.ok().body(questionService.getAllQuestions());
    }

    @RequestMapping(path = "/questions/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AmountOfQuestionTypeCollection> countQuestions() {
        return ResponseEntity.ok().body(questionService.countQuestions());
    }
}
