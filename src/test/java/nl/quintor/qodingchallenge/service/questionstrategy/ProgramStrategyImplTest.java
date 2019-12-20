package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.util.HttpRequestMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;

class ProgramStrategyImplTest {

    private ProgramStrategyImpl sut;
    private QuestionDAO mockedQuestionDAO;
    private HttpRequestMethods mockedHttpRequestMethods;

    @BeforeEach
    void setUp() throws SQLException {
        mockedQuestionDAO = Mockito.mock(QuestionDAO.class);
        Mockito.when(mockedQuestionDAO.getCodingQuestion(anyInt())).thenReturn(new CodingQuestionDTO());
        mockedHttpRequestMethods = Mockito.mock(HttpRequestMethods.class);
        sut = new ProgramStrategyImpl(mockedQuestionDAO);
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrectWhenExceptionIsThrown() {
        Mockito.when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenThrow(RuntimeException.class);
        sut.setRequestUtils(mockedHttpRequestMethods);
        QuestionDTO questionDTO = new QuestionDTO();
        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(QuestionState.INCORRECT.getState(), questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToCorrect() {
        Mockito.when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 1, 0))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);
        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(QuestionState.CORRECT.getState(), questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrect() {
        Mockito.when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 0, 1))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);

        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(QuestionState.INCORRECT.getState(), questionDTO.getStateID());
    }
}