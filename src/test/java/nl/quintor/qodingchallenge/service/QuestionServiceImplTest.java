package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAOImpl;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAOImpl;
import nl.quintor.qodingchallenge.service.exception.EmptyQuestionException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private final String jfall = "HC2 Holdings, Inc";
    private final String category = "category";
    private final int limit = 0;
    private final int questionId = 1;

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

        when(campaignDAOMock.campaignExists(jfall)).thenReturn(true);
    }

    @Test
    void getQuestionsCallsQuestionPercistenceGetQuestions() throws SQLException {
        sut.getQuestions(category, jfall);

        verify(questionDAOMock).getQuestions(category, limit);
    }

    @Test
    void getQuestionsCallsGetPossibleAnswer() throws SQLException {
        // Mock
        var list = setQuestionlist();
        when(questionDAOMock.getQuestions(category, limit)).thenReturn(list);
        when(campaignDAOMock.getAmountOfQuestions(anyString())).thenReturn(1);
        // Test
        sut.getQuestions(category, jfall);
        // Verify
        verify(questionDAOMock, times(limit)).getPossibleAnswers(questionId);
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerCorrect() throws SQLException {
        // Mock
        when(questionDAOMock.getCorrectAnswer(questionId)).thenReturn("");

        checkCorrectAnswerCorrectAndIncorrect();
    }

    @Test
    void setAnswerCallsQuestionPersistenceGetCorrectAnswerIncorrect() throws SQLException {
        // Mock
        when(questionDAOMock.getCorrectAnswer(questionId)).thenReturn("incorrect");

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
        sut.createQuestion(question);
        // Verify
        verify(questionDAOMock).persistOpenQuestion(question);
    }

    @Test
    void getQuestionsCallsGetPossibleAnswers() throws SQLException {
        // Mock
        var list = setQuestionlist();
        when(questionDAOMock.getQuestions(category, limit)).thenReturn(list);
        when(campaignDAOMock.getAmountOfQuestions(anyString())).thenReturn(1);
        // Test
        sut.getQuestions(category, jfall);
        // Verify
        verify(questionDAOMock, times(limit)).getPossibleAnswers(questionId);
    }

    @Test
    void getQuestionThrowsNoCampaignFoundException() throws SQLException {
        when(campaignDAOMock.campaignExists("This campaign does not exist")).thenReturn(true);

        assertThrows(NoCampaignFoundException.class, () -> sut.getQuestions(category, "This campaign does not exists"));
    }

    @Test
    void getQuestionsGetAllPossibleAnswersByQuestion() throws SQLException {
        List<QuestionDTO> questionDTOList = setQuestionlist();
        when(campaignDAOMock.getAmountOfQuestions(jfall)).thenReturn(1);
        when(questionDAOMock.getQuestions(category, campaignDAOMock.getAmountOfQuestions(jfall))).thenReturn(questionDTOList);
        when(questionDAOMock.getPossibleAnswers(questionId)).thenReturn(getPossibleAnswers());

        questionDTOList.get(0).setPossibleAnswers(getPossibleAnswers());

        assertEquals(questionDTOList, sut.getQuestions(category, jfall));
    }

    @Test
    void createQuestionThrowsEmptyQuestionException() {
        QuestionDTO questionDTO = getEmptyQuestion();

        assertThrows(EmptyQuestionException.class, () -> sut.createQuestion(questionDTO));
    }

    @Test
    void createQuestionCallsPersistMultipleQuestion() throws SQLException {
        // Mock
        var question = getMultipleQuestion();
        // Test
        sut.createQuestion(question);
        // Verify
        verify(questionDAOMock).persistMultipleQuestion(question);

    }

    private void checkCorrectAnswerCorrectAndIncorrect() throws SQLException {
        sut.setAnswer(setQuestionCollection());
        verify(questionDAOMock).getCorrectAnswer(questionId);
    }

    private List<QuestionDTO> setQuestionlist() throws SQLException {
        List<QuestionDTO> testValue = sut.getQuestions(category, jfall);
        QuestionDTO questionDTO = new QuestionDTO(questionId, "String", "Java", "multiple", "String");
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

    private ArrayList<PossibleAnswerDTO> getPossibleAnswers() {
        return new ArrayList<>() {{
            add(new PossibleAnswerDTO("yes", 1));
            add(new PossibleAnswerDTO("no", 0));
        }};
    }

    private QuestionDTO getEmptyQuestion() {
        return new QuestionDTO(2, "", "multiple", "String");
    }

}