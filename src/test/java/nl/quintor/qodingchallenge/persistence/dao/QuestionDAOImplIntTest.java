package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

class QuestionDAOImplIntTest {

    private final int QUESTION_ID = 3;
    private final int AMOUNT_OF_QUESTIONS = 3;
    private QuestionDAOImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new QuestionDAOImpl();
        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testQuestionDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getQuestionsReturnsQuestionsWithALimit() throws SQLException {
        String CATEGORY = "JAVA";
        int AMOUNT_OF_QUESTIONS = 3;
        List<QuestionDTO> questionDTOList = sut.getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS);

        assertEquals(AMOUNT_OF_QUESTIONS, questionDTOList.size());
    }

    @Test
    void getPossibleAnswerReturnsPossibleAnswers() throws SQLException {
        List<PossibleAnswerDTO> possibleAnswers = sut.getPossibleAnswers(QUESTION_ID);

        int AMOUNT_OF_ANSWERS = 2;
        assertEquals(AMOUNT_OF_ANSWERS, possibleAnswers.size());
    }

    @Test
    void setAnswerAddsOneMoreAnswerToQuestion() {
        assertDoesNotThrow(() -> sut.setAnswer(getQuestions(), 1, 1));
    }

    @Test
    void getCorrectAnswerGivesAllCorrectAnswers() throws SQLException {
        String actualResult = sut.getCorrectAnswer(QUESTION_ID);

        assertFalse(actualResult.isEmpty());
    }

    @Test
    void persistOpenQuestionPersistsOpenQuestion() throws SQLException {
        // Mock

        // Test
        sut.persistOpenQuestion(getQuestions());
        // Verify
        assertEquals(AMOUNT_OF_QUESTIONS + 1, sut.getAllQuestions().size());
    }

    @Test
    void getAllQuestionsReturnsAllQuestions() throws SQLException {
        // Mock

        // Test
        var testValue = sut.getAllQuestions();
        // Verify
        assertEquals(AMOUNT_OF_QUESTIONS, testValue.size());
    }

    @Test
    void getPendingAnswersReturnPendingAnswers() throws SQLException {
        //Mock

        //Test
        var testValue = sut.getPendingAnswers(1,1);
        int expectedLength = 1;

        //Verify
        assertEquals(expectedLength, testValue.size());
    }

    @Test
    void getPendingAnswersThrowsSqlException() throws SQLException{
        //TODO: when connection is mockable, add
    }

    @Test
    void getQuestionReturnQuestion() throws SQLException, NoQuestionFoundException{
        //Mock

        //Test
        var testValue = sut.getQuestion(1);
        int expectedLength = 1;

        //Verify
        assertEquals(expectedLength, testValue.getQuestionID());
    }

    @Test
    void getQuestionReturnQuestionThrowsSqlException() throws SQLException, NoQuestionFoundException{
        //TODO: when connection is mockable, add
    }

    @Test
    void getQuestionReturnQuestionThrowsNoQuestionFound() throws SQLException, NoQuestionFoundException{
        //Mock

        //Test

        //Verify
        assertThrows(NoQuestionFoundException.class, () -> {
            sut.getQuestion(50);
        });
    }

    @Test
    void setAnswerAddAmountPlusOne() throws SQLException{
        //Mock
        var oldLengthValue = sut.getPendingAnswers(1,1).size();

        //Test
        sut.setPendingAnswer(new GivenAnswerDTO(3,1,1,2,"Test"));

        //Verify
        assertEquals(oldLengthValue - 1, sut.getPendingAnswers(1,1).size());
    }

    @Test
    void setAnswerThrowsSqlException() throws SQLException{
        //TODO: when connection is mockable, add
    }

    private QuestionDTO getQuestions() {
        return new QuestionDTO(10, "dit is een test vraag", "open", null);
    }
}
