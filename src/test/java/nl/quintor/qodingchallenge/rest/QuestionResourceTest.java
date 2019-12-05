package nl.quintor.qodingchallenge.rest;

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

    private final String CATEGORY = "java";
    private final String JFALL = "JFALL";
    private final String ATTACHMENT = "java";
    private final String QUESTION = "Dit is mijn vraag";

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
        when(questionServiceMock.getQuestions(CATEGORY, JFALL)).thenReturn(getQuestionCollection().getQuestions());

        sut.sendQuestions(JFALL);

        verify(questionServiceMock).getQuestions(CATEGORY, JFALL);
    }

    @Test
    void sendQuestionsResturnsResponseOK() throws SQLException {
        var test = sut.sendQuestions(JFALL);

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
        questions.add(0, new QuestionDTO(2, CATEGORY, QUESTION, ATTACHMENT));
        questions.add(1, new QuestionDTO(3, CATEGORY, QUESTION, ATTACHMENT));
        return questions;
    }

    private QuestionCollection getQuestionCollection() {
        return new QuestionCollection(1, JFALL, getQuestions());
    }

    private QuestionDTO getQuestion() {
        return new QuestionDTO(1, CATEGORY, QUESTION, ATTACHMENT);
    }

}
