package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.service.QuestionType;
import nl.quintor.qodingchallenge.service.exception.CannotPersistQuestionException;
import nl.quintor.qodingchallenge.util.HttpRequestMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Objects;

public class ProgramStrategyImpl extends QuestionStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramStrategyImpl.class);
    private HttpRequestMethods requestUtils;

    public ProgramStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, QuestionType.PROGRAM);
        requestUtils = new HttpRequestMethods();
    }

    void setRequestUtils(HttpRequestMethods requestUtils) {
        this.requestUtils = requestUtils;
    }

    @Override
    public void validateAnswer(QuestionDTO question) {
        boolean result = runUnitTest(question);

        if (result) question.setStateID(QuestionState.CORRECT.getState());
        else question.setStateID(QuestionState.INCORRECT.getState());

    }

    private boolean runUnitTest(QuestionDTO question) {
        try {
            CodingQuestionDTO questionInDatabase = questionDAO.getCodingQuestion(question.getQuestionID());
            CodingQuestionDTO codingQuestionDTO = new CodingQuestionDTO(question.getGivenAnswer(), questionInDatabase.getTest());
            ResponseEntity<?> result = requestUtils.post("http://localhost:8090/validator/java/test", codingQuestionDTO, TestResultDTO.class);
            TestResultDTO testResult = (TestResultDTO) Objects.requireNonNull(result.getBody());

            return result.getStatusCode() != HttpStatus.EXPECTATION_FAILED &&
                    testResult.getTotalTestsFailed() <= 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void persistQuestion(QuestionDTO question) throws SQLException {
        boolean result = runUnitTest(question);
        if (result) questionDAO.persistProgramQuestion(question);
        else {
            throw new CannotPersistQuestionException("Could not persist programming message.", "Could either not compile the tests of the tests failed.", "Alter Unit Tests.");
        }
    }
}
