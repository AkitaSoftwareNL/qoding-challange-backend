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
        final int AMOUNT_OF_QUESTIONS = 3;
        when(questionServiceMock.getQuestions(category, campaign)).thenReturn(getQuestionCollection());

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
        // Verify
        var testValue = sut.getAllQuestions();
        // Test
        assertEquals(getQuestions(), testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    private List<QuestionDTO> getQuestions() {
        List<QuestionDTO> questions = new ArrayList<>();
        questions.add(0, new QuestionDTO(2, question, category, "open", attachment));
        questions.add(1, new QuestionDTO(3, question, category, "open", attachment));
        return questions;
    }

    @Test
    void getPendingAnswersCallsGetPendingAnswers() throws SQLException {
        // Mock

        // Verify
        sut.getPendingAnswers(1,1);
        // Test
        verify(questionServiceMock).getPendingAnswers(1,1);
    }

    @Test
    void getPendingAnswersReturnListAndStatusCodeOK() throws SQLException {
        // Mock
        var Answers = getAnswers();
        when(questionServiceMock.getPendingAnswers(1,1)).thenReturn(Answers);

        // Verify
        var testValue = sut.getPendingAnswers(1,1);
        // Test
        assertEquals(Answers, testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }

    private List<GivenAnswerDTO> getAnswers() {
        List<GivenAnswerDTO> answers = new ArrayList<>();
        answers.add(0, new GivenAnswerDTO(1, 1, 1, 1, "A"));
        answers.add(1, new GivenAnswerDTO(2, 2, 2, 1, "B"));
        return answers;
    }

    @Test
    void setPendingAnswersCallsSetPendingAnswers() throws SQLException {
        // Mock
        GivenAnswerDTO givenAnswerDTO = new GivenAnswerDTO();

        // Verify
        sut.setPendingAnswer(givenAnswerDTO);
        // Test
        verify(questionServiceMock).setPendingAnswer(givenAnswerDTO);
    }

    @Test
    void getQuestionCallsGetQuestion() throws SQLException {
        // Mock

        // Verify
        sut.getQuestion(1);
        // Test
        verify(questionServiceMock).getQuestion(1);
    }

    @Test
    void getQuestionReturnQuestion() throws SQLException {
        // Mock
        var question = getQuestion();
        when(questionServiceMock.getQuestion(1)).thenReturn(question);

        // Verify
        var testValue = sut.getQuestion(1);
        // Test
        assertEquals(question, testValue.getBody());
        assertEquals(HttpStatus.OK, testValue.getStatusCode());
    }


    private QuestionCollection getQuestionCollection() {
        return new QuestionCollection(1, 1, campaign, getQuestions());
    }

    private QuestionDTO getQuestion() {
        return new QuestionDTO(1, question, category, "open", attachment);
    }

}
