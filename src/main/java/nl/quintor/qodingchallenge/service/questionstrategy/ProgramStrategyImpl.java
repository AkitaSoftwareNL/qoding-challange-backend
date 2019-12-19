package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.service.QuestionType;
import nl.quintor.qodingchallenge.util.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProgramStrategyImpl extends QuestionStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramStrategyImpl.class);
    private HttpRequestUtils requestUtils;

    public ProgramStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, QuestionType.PROGRAM);
        requestUtils = new HttpRequestUtils();
    }

    void setRequestUtils(HttpRequestUtils requestUtils) {
        this.requestUtils = requestUtils;
    }

    @Override
    public void validateAnswer(QuestionDTO question) {
        try {
            CodingQuestionDTO questionInDatabase = questionDAO.getCodingQuestion(question.getQuestionID());
            CodingQuestionDTO codingQuestionDTO = new CodingQuestionDTO(question.getGivenAnswer(), questionInDatabase.getTest());
            ResponseEntity<?> result = requestUtils.post("http://localhost:8090/validator/java/test", codingQuestionDTO, TestResultDTO.class);
            TestResultDTO testResult = (TestResultDTO) result.getBody();

            if (result.getStatusCode() == HttpStatus.EXPECTATION_FAILED ||
                    testResult.getTotalTestsFailed() > 0) {
                question.setStateID(QuestionState.INCORRECT.getState());
            } else {
                question.setStateID(QuestionState.CORRECT.getState());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            question.setStateID(QuestionState.INCORRECT.getState());
        }

    }
}
