package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class QuestionResourceTest {

    private static final String CATEGORY = "java";
    private static final int AMOUNT_OF_QUESTIONS = 3;
    private static final String QUESTION = "Dit is mijn vraag";
    private static final String JAVA = "java";
    private static final String JFALL = "JFALL";

    private QuestionResource sut;
    private QuestionService questionServiceMock;
    private QuestionCollection questionCollection;

    @Before
    public void setUp() {
        this.sut = new QuestionResource();
        this.questionServiceMock = mock(QuestionService.class);
        this.sut.setQuestionService(questionServiceMock);
    }

    @Test
    public void sendQuestionCallsQuestionServiceGetQuestions() throws SQLException {
        setQuestion();
        when(questionServiceMock.getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS)).thenReturn(questionCollection.getQuestions());

        sut.sendQuestions(JFALL);

        verify(questionServiceMock).getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS);
    }

    @Test
    public void sendQuestionsResturnsResponseOK() throws SQLException {
        var test = sut.sendQuestions(JFALL);

        assertEquals(HttpStatus.OK.toString(), test.getStatusCode().toString());
    }

    @Test
    public void getAnswerCallsQuestionServiceSetAnswer() throws SQLException {
        setQuestion();
        sut.getAnswer(questionCollection);

        verify(questionServiceMock).setAnswer(questionCollection);
    }

    @Test
    public void getAnswerReturnsResponseOK() throws SQLException {
        var test = sut.getAnswer(questionCollection);

        assertEquals(HttpStatus.OK.toString(), test.getStatusCode().toString());
    }

    private void setQuestion() {
        var question = new QuestionDTO(2, CATEGORY, QUESTION, JAVA);
        var question2 = new QuestionDTO(3, CATEGORY, QUESTION, JAVA);
        List<QuestionDTO> questions = new ArrayList<>();
        questions.add(0, question);
        questions.add(1, question2);
        questionCollection = new QuestionCollection();
        questionCollection.setCampaignName(JFALL);
        questionCollection.setParticipantID(1);
        questionCollection.setQuestions(questions);
    }

}
