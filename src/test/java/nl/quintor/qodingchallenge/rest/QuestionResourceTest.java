package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class QuestionResourceTest {

    private final String category = "java";
    private final String campaign = "campaign";
    private final String attachment = "attachment";
    private final String question = "Dit is mijn vraag";
    private final int questionID = 1;
    private final int campaignID = 1;
    private final int pendingState = 1;

    private QuestionResource sut;
    private QuestionService questionServiceMock;

    @BeforeEach
    void setUp() {
        this.sut = new QuestionResource();
        this.questionServiceMock = mock(QuestionService.class);
        this.sut.setQuestionService(questionServiceMock);
    }

    @Test
    void sendQuestionCallsQuestionServiceGetQuestions() throws SQLException {
        when(questionServiceMock.getQuestions(category, campaign)).thenReturn(getQuestionCollection());


        sut.sendQuestions(campaign);

        verify(questionServiceMock).getQuestions(category, campaign);
    }

    @Test
    void sendQuestionsReturnsQuestionCollectionAndResponseOK() throws SQLException {
        when(questionServiceMock.getQuestions(category, campaign)).thenReturn(getQuestionCollection());

        var test = sut.sendQuestions(campaign);

        assertEquals(getQuestionCollection(), test.getBody());
        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    void getAnswerCallsQuestionServiceSetAnswer() throws SQLException {
        sut.getAnswer(getQuestionCollection());

        verify(questionServiceMock).setAnswer(getQuestionCollection());
    }

    @Test
    void getAnswerReturnsResponseOK() throws SQLException {
        var test = sut.getAnswer(getQuestionCollection());

        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    void createQuestionCallsCreateQuestion() throws SQLException {
        // Mock

        // Verify
        sut.createQuestion(getQuestion());
        // Test
        verify(questionServiceMock).createQuestion(getQuestion());
    }

    @Test
    void createQuestionReturnsStatusCodeOK() throws SQLException {
        // Mock

        // Verify
        var testValue = sut.createQuestion(getQuestion());
        // Test
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    @Test
    void getAllQuestionsCallsGetAllQuestions() throws SQLException {
        // Mock

        // Verify
        sut.getAllQuestions();
        // Test
        verify(questionServiceMock).getAllQuestions();
    }

    @Test
    void getAllQuestionsReturnsQuestionCollectionAndStatusCodeOK() throws SQLException {
        // Mock
        when(questionServiceMock.getAllQuestions()).thenReturn(getQuestions());
        QuestionCollection questionCollection = new QuestionCollection();
        questionCollection.setQuestions(getQuestions());
        // Test
        var testValue = sut.getAllQuestions();
        // Verify
        assertEquals(getQuestions(), testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    @Test
    void getPendingAnswersCallsGetPendingAnswers() throws SQLException {
        // Mock

        // Test
        sut.getPendingAnswers(campaignID, pendingState);
        // Verify
        verify(questionServiceMock).getPendingAnswers(campaignID, pendingState);
    }

    @Test
    void getPendingAnswersReturnAnswerListAndStatusCodeOK() throws SQLException {
        // Mock
        when(questionServiceMock.getPendingAnswers(campaignID, pendingState)).thenReturn(getAnswers());

        // Test
        var testValue = sut.getPendingAnswers(campaignID, pendingState);
        // Verify
        assertEquals(getAnswers(), testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    @Test
    void setPendingAnswerCallsSetPendingAnswer() throws SQLException {
        // Mock

        // Test
        sut.setPendingAnswer(campaignID, pendingState, getAnswerDTO());
        // Verify
        verify(questionServiceMock).setPendingAnswer(getAnswerDTO());
    }

    @Test
    void setPendingAnswerCallsGetPendingAnswers() throws SQLException {
        // Mock

        // Test
        sut.setPendingAnswer(campaignID, pendingState, getAnswerDTO());
        // Verify
        verify(questionServiceMock).getPendingAnswers(campaignID, pendingState);
    }

    @Test
    void setPendingAnswerReturnsAnswerListAndStatusCodeOK() throws SQLException {
        // Mock
        when(questionServiceMock.getPendingAnswers(campaignID, pendingState)).thenReturn(getAnswers());
        // Test
        var testValue = sut.setPendingAnswer(campaignID, pendingState, getAnswerDTO());
        // Verify
        assertEquals(getAnswers(), testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    @Test
    void getQuestionCallsGetQuestion() throws SQLException {
        // Mock

        // Test
        sut.getQuestion(questionID);
        // Verify
        verify(questionServiceMock).getQuestion(questionID);
    }

    @Test
    void getQuestionReturnQuestion() throws SQLException {
        // Mock
        when(questionServiceMock.getQuestion(questionID)).thenReturn(getQuestion());
        // Test
        var testValue = sut.getQuestion(questionID);
        // Verify
        assertEquals(getQuestion(), testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    @Test
    void removeQuestionCallsRemoveQuestion() throws SQLException {
        // Mock

        // Test
        sut.removeQuestion(questionID);
        // Verify
        verify(questionServiceMock).removeQuestion(questionID);
    }

    @Test
    void removeQuestionCallsGetAllQuestions() throws SQLException {
        // Mock

        // Test
        sut.removeQuestion(questionID);
        // Verify
        verify(questionServiceMock).getAllQuestions();
    }

    @Test
    void removeQuestionReturnsQuestionList() throws SQLException {
        // Mock
        when(questionServiceMock.getAllQuestions()).thenReturn(getQuestions());
        // Test
        var testValue = sut.removeQuestion(questionID);
        // Verify
        assertEquals(getQuestions(), testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    private GivenAnswerDTO getAnswerDTO() {
        return new GivenAnswerDTO();
    }

    private QuestionCollection getQuestionCollection() {
        return new QuestionCollection(1, 1, campaign, getQuestions());
    }

    private QuestionDTO getQuestion() {
        return new QuestionDTO(1, question, category, "open", attachment);
    }

    private List<GivenAnswerDTO> getAnswers() {
        List<GivenAnswerDTO> answers = new ArrayList<>();
        answers.add(0, new GivenAnswerDTO(1, 1, 1, 1, "A"));
        answers.add(1, new GivenAnswerDTO(2, 2, 2, 1, "B"));
        return answers;
    }

    private List<QuestionDTO> getQuestions() {
        List<QuestionDTO> questions = new ArrayList<>();
        questions.add(0, new QuestionDTO(2, question, category, "open", attachment));
        questions.add(1, new QuestionDTO(3, question, category, "open", attachment));
        return questions;
    }

}
