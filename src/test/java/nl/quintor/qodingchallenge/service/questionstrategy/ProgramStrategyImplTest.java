package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.utils.HttpRequestUtils;
import nl.quintor.qodingchallenge.service.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;

class ProgramStrategyImplTest {

    private final int incorrect = 3;
    private final int correct = 2;
    private ProgramStrategyImpl sut;
    private QuestionDAO mockedQuestionDAO;
    private HttpRequestUtils mockedHttpRequestUtils;

    @BeforeEach
    void setUp() throws SQLException {
        mockedQuestionDAO = Mockito.mock(QuestionDAO.class);
        Mockito.when(mockedQuestionDAO.getCodingQuestion(anyInt())).thenReturn(new CodingQuestionDTO());
        mockedHttpRequestUtils = Mockito.mock(HttpRequestUtils.class);
        sut = new ProgramStrategyImpl(mockedQuestionDAO);
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrectWhenExceptionIsThrown() {
        Mockito.when(mockedHttpRequestUtils.post(anyString(), any(), any())).thenThrow(ValidationException.class);
        sut.setRequestUtils(mockedHttpRequestUtils);
        QuestionDTO questionDTO = new QuestionDTO();
        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(incorrect, questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToCorrect() {
        Mockito.when(mockedHttpRequestUtils.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 1, 0))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestUtils);
        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(correct, questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrect() {
        Mockito.when(mockedHttpRequestUtils.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 0, 1))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestUtils);

        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(incorrect, questionDTO.getStateID());
    }
}