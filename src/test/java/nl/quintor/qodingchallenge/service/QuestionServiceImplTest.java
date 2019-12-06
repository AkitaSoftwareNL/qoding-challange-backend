package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAOImpl;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAOImpl;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private final String JFALL = "HC2 Holdings, Inc";
    private final String CATEGORY = "category";
    private final int LIMIT = 0;
    private final int QUESTION_ID = 1;
    private final List<PossibleAnswerDTO> POSSIBLE_ANSWER = new ArrayList<>();

    private QuestionDAO questionDAOMock;
    private CampaignDAO campaignDAOMock;
    private QuestionServiceImpl sut;

    @BeforeEach
    void setUp() throws SQLException {
        sut = new QuestionServiceImpl();

        this.questionDAOMock = mock(QuestionDAOImpl.class);
        this.campaignDAOMock = mock(CampaignDAOImpl.class);

        this.sut.setQuestionDAO(questionDAOMock);
        this.sut.setCampaignDAO(campaignDAOMock);

        when(campaignDAOMock.campaignExists(JFALL)).thenReturn(true);

        POSSIBLE_ANSWER.add(new PossibleAnswerDTO("yes", 1));
        POSSIBLE_ANSWER.add(new PossibleAnswerDTO("no", 0));
    }

    @Test
    void getQuestionsCallsQuestionPercistenceGetQuestions() throws SQLException {
        sut.getQuestions(CATEGORY, JFALL);

        verify(questionDAOMock).getQuestions(CATEGORY, LIMIT);
    }

    @Test
    void getQuestionsCallsGetPossibleAnswer() throws SQLException {
        // Mock
        var list = setQuestionlist();
        when(questionDAOMock.getQuestions(CATEGORY, LIMIT)).thenReturn(list);
        when(campaignDAOMock.getAmountOfQuestions(anyString())).thenReturn(1);
        // Test
        sut.getQuestions(CATEGORY, JFALL);
        // Verify
        verify(questionDAOMock, times(LIMIT)).getPossibleAnswers(QUESTION_ID);
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerCorrect() throws SQLException {
        // Mock
        when(questionDAOMock.getCorrectAnswer(QUESTION_ID)).thenReturn("");

        checkCorrectAnswerCorrectAndIncorrect();
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerIncorrect() throws SQLException {
        // Mock
        when(questionDAOMock.getCorrectAnswer(QUESTION_ID)).thenReturn("incorrect");

        checkCorrectAnswerCorrectAndIncorrect();
    }

    @Test
    void getAllQuestionsCallsGetAllQuestions() throws SQLException {
        // Mock

        // Test
        sut.getAllQuestions();
        // Verify
        verify(questionDAOMock).getAllQuestions();
    }

    @Test
    void getAllQuestionsReturnsQuestionList() throws SQLException {
        // Mock
        var questions = setQuestionlist();
        when(questionDAOMock.getAllQuestions()).thenReturn(questions);
        // Test
        var testValue = sut.getAllQuestions();
        // Verify
        assertEquals(questions, testValue);
    }

    @Test
    void createQuestionCallsPersistOpenQuestion() throws SQLException {
        // Mock
        var question = getOpenQuestion();
        // Test
        sut.createQuestion(getOpenQuestion());
        // Verify
        verify(questionDAOMock).persistOpenQuestion(getOpenQuestion());

        // Mock
        var list = setQuestionlist();
        when(questionDAOMock.getQuestions(CATEGORY, LIMIT)).thenReturn(list);
        when(campaignDAOMock.getAmountOfQuestions(anyString())).thenReturn(1);
        // Test
        sut.getQuestions(CATEGORY, JFALL);
        // Verify
        verify(questionDAOMock, times(LIMIT)).getPossibleAnswers(QUESTION_ID);
    }

    @Test
    void getQuestionThrowsNoCampaignFoundException() throws SQLException {
        when(campaignDAOMock.campaignExists("This campaign does not exist")).thenReturn(true);

        assertThrows(NoCampaignFoundException.class, () -> sut.getQuestions(CATEGORY, "This campaign does not exists"));
    }

    @Test
    void getQuestionsGetAllPossibleAnswersByQuestion() throws SQLException {
        List<QuestionDTO> questionDTOList = setQuestionlist()
        when(campaignDAOMock.getAmountOfQuestions(JFALL)).thenReturn(1);
        when(questionDAOMock.getQuestions(CATEGORY, campaignDAOMock.getAmountOfQuestions(JFALL))).thenReturn(questionDTOList);
        when(questionDAOMock.getPossibleAnswers(QUESTION_ID)).thenReturn(POSSIBLE_ANSWER);

        questionDTOList.get(0).setPossibleAnswers(POSSIBLE_ANSWER);

        assertEquals(questionDTOList, sut.getQuestions(CATEGORY, JFALL));
    }

    private void checkCorrectAnswerCorrectAndIncorrect() throws SQLException {
        sut.setAnswer(setQuestionCollection());
        verify(questionDAOMock).getCorrectAnswer(QUESTION_ID);
    }

    private List<QuestionDTO> setQuestionlist() throws SQLException {
        List<QuestionDTO> testValue = sut.getQuestions(CATEGORY, JFALL);
        QuestionDTO questionDTO = new QuestionDTO(QUESTION_ID, "String", "Java", "multiple", "String");
        testValue.add(questionDTO);
        return testValue;
    }

    private QuestionCollection setQuestionCollection() throws SQLException {
        return new QuestionCollection(1, "test", setQuestionlist());
    }

    private QuestionDTO getOpenQuestion() {
        return new QuestionDTO(2, "String", "JAVA", "open", "String");
    }

    private QuestionDTO getMultipleQuestion() {
        return new QuestionDTO(2, "String", "JAVA", "multiple", "String");
    }
}
