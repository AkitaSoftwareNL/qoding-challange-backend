package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
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
        when(questionServiceMock.getQuestions(category, campaign)).thenReturn(getQuestionCollection().getQuestions());

        sut.sendQuestions(campaign);

        verify(questionServiceMock).getQuestions(category, campaign);
    }

    @Test
    void sendQuestionsResturnsResponseOK() throws SQLException {
        var test = sut.sendQuestions(campaign);

        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    void getAnswerCallsQuestionServiceSetAnswer() throws SQLException {
        var questions = getQuestionCollection();
        sut.getAnswer(questions);

        verify(questionServiceMock).setAnswer(questions);
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
        var questions = getQuestions();
        when(questionServiceMock.getAllQuestions()).thenReturn(questions);
        QuestionCollection questionCollection = new QuestionCollection();
        questionCollection.setQuestions(questions);
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
        sut.getPendingAnswers(campaignID,pendingState);
        // Verify
        verify(questionServiceMock).getPendingAnswers(campaignID,pendingState);
    }

    @Test
    void getPendingAnswersReturnListAndStatusCodeOK() throws SQLException {
        // Mock
        var Answers = getAnswers();
        when(questionServiceMock.getPendingAnswers(campaignID,pendingState)).thenReturn(Answers);

        // Test
        var testValue = sut.getPendingAnswers(campaignID,pendingState);
        // Verify
        assertEquals(Answers, testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    @Test
    void setPendingAnswersCallsSetPendingAnswers() throws SQLException {
        // Mock
        GivenAnswerDTO givenAnswerDTO = new GivenAnswerDTO();

        // Test
        sut.setPendingAnswer(givenAnswerDTO);
        // Verify
        verify(questionServiceMock).setPendingAnswer(givenAnswerDTO);
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
        var question = getQuestion();
        when(questionServiceMock.getQuestion(questionID)).thenReturn(question);

        // Test
        var testValue = sut.getQuestion(questionID);
        // Verify
        assertEquals(question, testValue.getBody());
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

    private QuestionCollection getQuestionCollection() {
        return new QuestionCollection(1, campaign, getQuestions());
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
