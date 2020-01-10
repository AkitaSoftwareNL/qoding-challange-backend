package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.service.exception.CannotPersistQuestionException;
import nl.quintor.qodingchallenge.util.HttpRequestMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProgramStrategyImplTest {

    private ProgramStrategyImpl sut;
    private QuestionDAO mockedQuestionDAO;
    private HttpRequestMethods mockedHttpRequestMethods;

    @BeforeEach
    void setUp() throws SQLException {
        mockedQuestionDAO = mock(QuestionDAO.class);
        when(mockedQuestionDAO.getCodingQuestion(anyInt())).thenReturn(new CodingQuestionDTO());
        mockedHttpRequestMethods = mock(HttpRequestMethods.class);
        sut = new ProgramStrategyImpl(mockedQuestionDAO);
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrectWhenExceptionIsThrown() throws SQLException {
        when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenThrow(RuntimeException.class);
        sut.setRequestUtils(mockedHttpRequestMethods);
        QuestionDTO questionDTO = new QuestionDTO();
        sut.validateAnswer(questionDTO);
        assertEquals(QuestionState.INCORRECT.getState(), questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToCorrect() throws SQLException {
        when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 1, 0))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);
        sut.validateAnswer(questionDTO);
        assertEquals(QuestionState.CORRECT.getState(), questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrect() throws SQLException {
        when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 0, 1))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);

        sut.validateAnswer(questionDTO);
        assertEquals(QuestionState.INCORRECT.getState(), questionDTO.getStateID());
    }

    @Test
    void persistQuestionThrowsCannotPersistException() {
        when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 0, 1)));

        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);

        assertThrows(CannotPersistQuestionException.class, () -> sut.persistQuestion(questionDTO));
    }

    @Test
    void persistQuestionNotThrowsCannotPersistException() {
        when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 1, 0)));

        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);

        assertDoesNotThrow(() -> sut.persistQuestion(questionDTO));
    }

    @Test
    void persistQuestionCallsPersistQuestion() throws SQLException {
        when(mockedHttpRequestMethods.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 1, 0)));

        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestMethods);

        sut.persistQuestion(questionDTO);
        verify(mockedQuestionDAO).persistProgramQuestion(questionDTO);
    }


}
