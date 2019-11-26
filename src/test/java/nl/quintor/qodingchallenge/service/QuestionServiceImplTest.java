package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private final String CATEGORY = "category";
    private final int LIMIT = 1;
    private final int QUESTION_ID = 1;

    private QuestionPersistence questionPersistenceMock;
    private QuestionServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new QuestionServiceImpl();
        this.questionPersistenceMock = mock(QuestionDAO.class);
        this.sut.setQuestionPersistence(questionPersistenceMock);
    }

    @Test
    void getQuestionsCallsQuestionPercistenceGetQuestions() throws SQLException {
        sut.getQuestions(CATEGORY, LIMIT);

        verify(questionPersistenceMock).getQuestions(CATEGORY, LIMIT);
    }

    @Test
    void getQuestionsCallsGetPossibleAnswer() throws SQLException {
        // Mock
        var list = setQuestionlist();
        when(questionPersistenceMock.getQuestions(CATEGORY, LIMIT)).thenReturn(list);
        // Test
        sut.getQuestions(CATEGORY, LIMIT);
        // Verify
        verify(questionPersistenceMock, times(LIMIT)).getPossibleAnswers(QUESTION_ID);
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerCorrect() throws SQLException {
        // Mock
        when(questionPersistenceMock.getCorrectAnswer(QUESTION_ID)).thenReturn("");
        // Test
        sut.setAnswer(setQuestionCollection());
        // Verify
        verify(questionPersistenceMock).getCorrectAnswer(QUESTION_ID);
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerIncorrect() throws SQLException {
        // Mock
        when(questionPersistenceMock.getCorrectAnswer(QUESTION_ID)).thenReturn("incorrect");
        // Test
        sut.setAnswer(setQuestionCollection());
        // Verify
        verify(questionPersistenceMock).getCorrectAnswer(QUESTION_ID);
    }

    private List<QuestionDTO> setQuestionlist() throws SQLException {
        List<QuestionDTO> testValue = sut.getQuestions(CATEGORY, LIMIT);
        QuestionDTO questionDTO = new QuestionDTO(QUESTION_ID, "String", "multiple", "String");
        testValue.add(questionDTO);
        return testValue;
    }

    private QuestionCollection setQuestionCollection() throws SQLException {
        return new QuestionCollection(1,"test", setQuestionlist());
    }
}