package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.HttpRequestUtils;
import nl.quintor.qodingchallenge.service.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CodingStrategyImplTest {

    private CodingStrategyImpl sut;
    private QuestionDAO mockedQuestionDAO;
    private HttpRequestUtils mockedHttpRequestUtils;

    @BeforeEach
    void setUp() {
        mockedQuestionDAO = Mockito.mock(QuestionDAO.class);
        mockedHttpRequestUtils = Mockito.mock(HttpRequestUtils.class);
        sut = new CodingStrategyImpl(mockedQuestionDAO);
    }

    @Test
    void validateAnswerThrowsValidationException() {
        Mockito.when(mockedHttpRequestUtils.post(anyString(), any(), any())).thenThrow(ValidationException.class);
        sut.setRequestUtils(mockedHttpRequestUtils);
        Assertions.assertThrows(ValidationException.class, () -> {
            sut.validateAnswer(new QuestionDTO());
        });
    }

    @Test
    void validateAnswerSetsQuestionStateToCorrect() {
        Mockito.when(mockedHttpRequestUtils.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 1, 0))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestUtils);
        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(2, questionDTO.getStateID());
    }

    @Test
    void validateAnswerSetsQuestionStateToIncorrect() {
        Mockito.when(mockedHttpRequestUtils.post(anyString(), any(), any())).thenReturn(
                ResponseEntity.ok(new TestResultDTO(1, 0, 1))
        );
        QuestionDTO questionDTO = new QuestionDTO();
        sut.setRequestUtils(mockedHttpRequestUtils);
        sut.validateAnswer(questionDTO);
        Assertions.assertEquals(3, questionDTO.getStateID());
    }
}