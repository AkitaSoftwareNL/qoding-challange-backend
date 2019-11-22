package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.GivenAnswerDTOCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.service.IQuestionService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class QuestionResourceTest {

    private static final String CATEGORY = "java";
    private static final int AMOUNT_OF_QUESTIONS = 3;
    private static final String QUESTION = "Dit is mijn vraag";
    private static final String JAVA = "java";
    private static final String JFALL = "JFALL";
    private static final int PARTICIPANT_ID = 1;
    private static final String GOOD = "Good";

    private QuestionResource sut;
    private IQuestionService questionServiceMock;
    private List<QuestionDTO> questionDTOList;
    private GivenAnswerDTOCollection answers = new GivenAnswerDTOCollection();

    @Before
    public void setUp() {
        this.sut = new QuestionResource();
        this.questionServiceMock = mock(IQuestionService.class);
        this.sut.setQuestionService(questionServiceMock);
    }

    @Test
    public void sendQuestionCallsQuestionServiceGetQuestions() throws SQLException {
        setQuestion();
        when(questionServiceMock.getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS)).thenReturn(questionDTOList);

        sut.sendQuestions();

        verify(questionServiceMock).getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS);
    }

    @Test
    public void sendQuestionsResturnsResponseOK() throws SQLException {
        setQuestion();
        var test = sut.sendQuestions();

        assertEquals(HttpStatus.OK.toString(), test.getStatusCode().toString());
    }

    @Test
    public void getAnswerCallsQuestionServiceSetAnswer() throws SQLException {
        setAnswers();
        sut.getAnswer(answers);

        verify(questionServiceMock).setAnswer(answers);
    }

    @Test
    public void getAnswerReturnsResponseOK() throws SQLException {
        setAnswers();
        var test = sut.getAnswer(answers);

        assertEquals(HttpStatus.OK.toString(), test.getStatusCode().toString());
    }

    private void setQuestion() {
        var question = new QuestionDTO(2, CATEGORY, QUESTION, JAVA);
        var question2 = new QuestionDTO(3, CATEGORY, QUESTION, JAVA);
        questionDTOList = new ArrayList<>();
        questionDTOList.add(0, question);
        questionDTOList.add(PARTICIPANT_ID, question2);
    }

    private void setAnswers() {
        var answerDTO = new GivenAnswerDTO(PARTICIPANT_ID, PARTICIPANT_ID, JFALL, 0, GOOD);
        var answerDTO2 = new GivenAnswerDTO(2, PARTICIPANT_ID, JFALL, 0, GOOD);
        List<GivenAnswerDTO> answerDTOSList = new ArrayList<>();
        answerDTOSList.add(0, answerDTO);
        answerDTOSList.add(PARTICIPANT_ID, answerDTO2);
        answers.setGivenAnswerDTO(answerDTOSList);
    }
}
