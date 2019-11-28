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
        int AMOUNT_OF_QUESTIONS = 3;
        when(questionServiceMock.getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS, JFALL)).thenReturn(setQuestionCollection().getQuestions());

        sut.sendQuestions(JFALL);

        verify(questionServiceMock).getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS, JFALL);
    }

    @Test
    void sendQuestionsResturnsResponseOK() throws SQLException {
        var test = sut.sendQuestions(JFALL);

        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    void getAnswerCallsQuestionServiceSetAnswer() throws SQLException {
        var questions = setQuestionCollection();
        sut.getAnswer(questions);

        verify(questionServiceMock).setAnswer(questions);
    }

    @Test
    void getAnswerReturnsResponseOK() throws SQLException {
        var test = sut.getAnswer(setQuestionCollection());

        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    private List<QuestionDTO> setQuestion() {
        List<QuestionDTO> questions = new ArrayList<>();
        String JAVA = "java";
        String QUESTION = "Dit is mijn vraag";
        questions.add(0, new QuestionDTO(2, CATEGORY, QUESTION, JAVA));
        questions.add(1, new QuestionDTO(3, CATEGORY, QUESTION, JAVA));
        return questions;
    }

    private QuestionCollection setQuestionCollection() {
        return new QuestionCollection(1, JFALL, setQuestion());
    }

}
