package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private final String campaign = "HC2 Holdings, Inc";
    private final int campaignID = 1;
    private final String category = "JAVA";
    private final int questionLimit = 3;
    private final int questionId = 1;
    private final int pendingState = 1;

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

        when(campaignDAOMock.campaignExists(campaign)).thenReturn(true);
    }

    @Test
    void getQuestionsCallsGetQuestions() throws SQLException {
        sut.getQuestions(category, campaign);

        verify(questionDAOMock).getQuestions(category, questionLimit);
    }

    // TODO fix for loop.
    @Test
    void getQuestionsCallsGetPossibleAnswer() throws SQLException {
        // Mock
        var list = getQuestionlist();
        when(questionDAOMock.getQuestions(category, questionLimit)).thenReturn(list);
        when(campaignDAOMock.getAmountOfQuestions(anyString())).thenReturn(questionLimit);
        // Test
        sut.getQuestions(category, campaign);
        // Verify
        verify(questionDAOMock, times(0)).getPossibleAnswers(questionId);
    }

    @Test
    void setAnswerCallsGetCorrectAnswerCorrect() throws SQLException {
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
        when(questionDAOMock.getAllQuestions()).thenReturn(getQuestionlist());
        // Test
        var testValue = sut.getAllQuestions();
        // Verify
        assertEquals(getQuestionlist(), testValue);
    }

    @Test
    void createQuestionCallsPersistOpenQuestion() throws SQLException {
        // Mock

        // Test
        sut.createQuestion(getOpenQuestion());
        // Verify
        verify(questionDAOMock).persistOpenQuestion(getOpenQuestion());
    }

    // TODO fix for loop.
    @Test
    void getQuestionsCallsGetPossibleAnswers() throws SQLException {
        // Mock
        when(questionDAOMock.getQuestions(anyString(), anyInt())).thenReturn(getQuestionlist());
        when(campaignDAOMock.getAmountOfQuestions(anyString())).thenReturn(getQuestionlist().size());
        // Test
        sut.getQuestions(category, campaign);
        // Verify
        verify(questionDAOMock, times(1)).getPossibleAnswers(questionId);
    }

    @Test
    void getQuestionThrowsNoCampaignFoundException() throws SQLException {
        when(campaignDAOMock.campaignExists("This campaign does not exist")).thenReturn(true);

        assertThrows(NoCampaignFoundException.class, () -> sut.getQuestions(category, "This campaign does not exists"));
    }

    @Test
    void getQuestionsGetAllPossibleAnswersByQuestion() throws SQLException {
        List<QuestionDTO> questionDTOList = getQuestionlist();
        when(campaignDAOMock.getAmountOfQuestions(campaign)).thenReturn(1);
        when(questionDAOMock.getQuestions(category, campaignDAOMock.getAmountOfQuestions(campaign))).thenReturn(questionDTOList);
        when(questionDAOMock.getPossibleAnswers(questionId)).thenReturn(getPossibleAnswers());

        questionDTOList.get(0).setPossibleAnswers(getPossibleAnswers());

        assertEquals(questionDTOList, sut.getQuestions(category, campaign));
    }

    @Test
    void createQuestionThrowsEmptyQuestionException() {
        QuestionDTO questionDTO = getEmptyQuestion();

        assertThrows(EmptyQuestionException.class, () -> sut.createQuestion(questionDTO));
    }

    @Test
    void createQuestionCallsPersistMultipleQuestion() throws SQLException {
        // Mock

        // Test
        sut.createQuestion(getMultipleQuestion());
        // Verify
        verify(questionDAOMock).persistMultipleQuestion(getMultipleQuestion());

    }

    @Test
    void getQuestionCallsGetQuestion() throws SQLException {
        // Mock

        // Test
        sut.getQuestion(questionId);
        // Verify
        verify(questionDAOMock).getQuestion(questionId);
    }

    @Test
    void getQuestionReturnsQuestion() throws SQLException {
        // Mock
        when(questionDAOMock.getQuestion(questionId)).thenReturn(getOpenQuestion());
        // Test
        var testValue = sut.getQuestion(questionId);
        // Verify
        assertEquals(getOpenQuestion(), testValue);
    }

    @Test
    void getPendingAnswersCallsGetPendingAnswers() throws SQLException {
        // Mock

        // Test
        sut.getPendingAnswers(campaignID, pendingState);
        // Verify
        verify(questionDAOMock).getPendingAnswers(campaignID, pendingState);
    }

    @Test
    void getPendingAnswersReturnList() throws SQLException {
        // Mock
        when(questionDAOMock.getPendingAnswers(campaignID, pendingState)).thenReturn(getAnswers());
        // Test
        var testValue = sut.getPendingAnswers(campaignID, pendingState);
        // Verify
        assertEquals(getAnswers(), testValue);
    }

    @Test
    void setPendingAnswersCallsSetPendingAnswers() throws SQLException {
        // Mock
        GivenAnswerDTO givenAnswerDTO = new GivenAnswerDTO();

        // Test
        sut.setPendingAnswer(givenAnswerDTO);
        // Verify
        verify(questionDAOMock).setPendingAnswer(givenAnswerDTO);
    }

    @Test
    void removeQuestionCallRemoveQuestion() throws SQLException {
        // Mock

        // Test
        sut.removeQuestion(questionId);
        // Verify
        verify(questionDAOMock).removeQuestion(questionId);
    }

    private void checkCorrectAnswerCorrectAndIncorrect() throws SQLException {
        sut.setAnswer(getQuestionCollection());
        verify(questionDAOMock).getCorrectAnswer(questionId);
    }

    private List<GivenAnswerDTO> getAnswers() {
        List<GivenAnswerDTO> answers = new ArrayList<>();
        answers.add(0, new GivenAnswerDTO(1, 1, 1, 1, "A"));
        answers.add(1, new GivenAnswerDTO(2, 2, 2, 1, "B"));
        return answers;
    }

    private List<QuestionDTO> getQuestionlist() throws SQLException {
        return new ArrayList<>() {{
            new QuestionDTO(questionId, "String", category, "multiple", "String");
        }};
    }

    private QuestionCollection getQuestionCollection() throws SQLException {
        return new QuestionCollection(1, "test", getQuestionlist());
    }

    private QuestionDTO getOpenQuestion() {
        return new QuestionDTO(2, "String", category, "open", "String");
    }

    private QuestionDTO getMultipleQuestion() {
        return new QuestionDTO(2, "String", category, "multiple", "String");
    }

    private ArrayList<PossibleAnswerDTO> getPossibleAnswers() {
        return new ArrayList<>() {{
            add(new PossibleAnswerDTO("yes", 1));
            add(new PossibleAnswerDTO("no", 0));
        }};
    }

    private QuestionDTO getEmptyQuestion() {
        return new QuestionDTO(2, "", category, "multiple", "String");
    }

}