package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAOImpl;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAOImpl;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private final String JFALL = "HC2 Holdings, Inc";
    private final String CATEGORY = "category";
    private final int LIMIT = 1;
    private final int QUESTION_ID = 1;

    private QuestionDAO questionDAOMock;
    private CampaignDAO campaignDAOMock;
    private QuestionServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new QuestionServiceImpl();

        this.questionDAOMock = mock(QuestionDAOImpl.class);
        this.campaignDAOMock = mock(CampaignDAOImpl.class);

        this.sut.setQuestionDAO(questionDAOMock);
        this.sut.setCampaignDAO(campaignDAOMock);

        try {
            when(campaignDAOMock.campaignExists(JFALL)).thenReturn(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getQuestionsCallsQuestionPercistenceGetQuestions() throws SQLException {
        sut.getQuestions(CATEGORY, LIMIT, JFALL);

        verify(questionDAOMock).getQuestions(CATEGORY, LIMIT);
    }

    @Test
    void getQuestionsCallsGetPossibleAnswer() throws SQLException {
        // Mock
        var list = setQuestionlist();
        when(questionDAOMock.getQuestions(CATEGORY, LIMIT)).thenReturn(list);
        // Test
        sut.getQuestions(CATEGORY, LIMIT, JFALL);
        // Verify
        verify(questionDAOMock, times(LIMIT)).getPossibleAnswers(QUESTION_ID);
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerCorrect() throws SQLException {
        // Mock
        when(questionDAOMock.getCorrectAnswer(QUESTION_ID)).thenReturn("");
        // Test
        sut.setAnswer(setQuestionCollection());
        // Verify
        verify(questionDAOMock).getCorrectAnswer(QUESTION_ID);
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerIncorrect() throws SQLException {
        // Mock
        when(questionDAOMock.getCorrectAnswer(QUESTION_ID)).thenReturn("incorrect");
        // Test
        sut.setAnswer(setQuestionCollection());
        // Verify
        verify(questionDAOMock).getCorrectAnswer(QUESTION_ID);
    }

    private List<QuestionDTO> setQuestionlist() throws SQLException {
        List<QuestionDTO> testValue = sut.getQuestions(CATEGORY, LIMIT, JFALL);
        QuestionDTO questionDTO = new QuestionDTO(QUESTION_ID, "String", "multiple", "String");
        testValue.add(questionDTO);
        return testValue;
    }

    private QuestionCollection setQuestionCollection() throws SQLException {
        return new QuestionCollection(1, "test", setQuestionlist());
    }
}