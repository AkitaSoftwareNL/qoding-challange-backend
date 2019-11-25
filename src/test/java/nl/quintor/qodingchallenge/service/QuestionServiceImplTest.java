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

    private static final String CATEGORY = "category";
    private static final int LIMIT = 3;
    private static final int QUESTION_ID = 1;

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

    private List<QuestionDTO> setQuestionlist() throws SQLException {
        List<QuestionDTO> testValue = sut.getQuestions(CATEGORY, LIMIT);
        QuestionDTO questionDTO = new QuestionDTO(0, "String", "multiple", "String");
        testValue.add(questionDTO);
        return testValue;
    }
}