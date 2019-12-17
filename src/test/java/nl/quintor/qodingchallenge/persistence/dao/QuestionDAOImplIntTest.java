package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionDAOImplIntTest {

    private final int campaignId = 1;
    private final int questionState = 1;
    private final int questionId = 3;
    private final int amountOfQuestions = 4;
    private final int participentId = 1;
    private final String category = "JAVA";

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
        List<QuestionDTO> questionDTOList = sut.getQuestions(category, amountOfQuestions);

        assertEquals(amountOfQuestions, questionDTOList.size());
    }

    @Test
    void getPossibleAnswerReturnsPossibleAnswers() throws SQLException {
        int AMOUNT_OF_ANSWERS = 2;

        List<PossibleAnswerDTO> possibleAnswers = sut.getPossibleAnswers(questionId);

        assertEquals(AMOUNT_OF_ANSWERS, possibleAnswers.size());
    }

    @Test
    void setAnswerSetsAnswer() throws SQLException {
        int oldLength = sut.getPendingAnswers(campaignId, questionState).size();

        sut.setAnswer(getOpenQuestion(), campaignId, participentId);

        assertEquals(oldLength + 1, sut.getPendingAnswers(campaignId, questionState).size());
    }

    @Test
    void getCorrectAnswerGivesAllCorrectAnswers() throws SQLException {
        String actualResult = sut.getCorrectAnswer(questionId);

        assertFalse(actualResult.isEmpty());
    }

    @Test
    void persistOpenQuestionPersistsOpenQuestion() throws SQLException {
        // Mock

        // Test
        sut.persistOpenQuestion(getOpenQuestion());
        // Verify
        assertEquals(amountOfQuestions + 1, sut.getAllQuestions().size());
    }

    @Test
    void getAllQuestionsReturnsAllQuestions() throws SQLException {
        // Mock

        // Test
        var testValue = sut.getAllQuestions();
        // Verify
        assertEquals(amountOfQuestions, testValue.size());
    }

    @Test
    void persistMultipleQuestionThrowsSQLException() {
        // Mock

        // Test

        // Verify
        assertThrows(SQLException.class, () -> sut.persistMultipleQuestion(getMultipleQuestion()));
    }

    @Test
    void getPendingAnswersReturnPendingAnswers() throws SQLException {
        int expectedLength = 1;
        //Mock

        //Test

        var testValue = sut.getPendingAnswers(campaignId, questionState);
        //Verify
        assertEquals(expectedLength, testValue.size());
    }

    @Test
    void getQuestionReturnQuestion() throws SQLException, NoQuestionFoundException {
        int expectedId = 3;
        //Mock

        //Test
        var testValue = sut.getQuestion(questionId);

        //Verify
        assertEquals(expectedId, testValue.getQuestionID());
    }

    @Test
    void getQuestionReturnQuestionThrowsNoQuestionFound() throws NoQuestionFoundException {
        int falseId = 50;
        //Mock

        //Test

        //Verify
        assertThrows(NoQuestionFoundException.class, () -> sut.getQuestion(falseId));
    }

    @Test
    void setPendingAnswerAddAmountPlusOne() throws SQLException {
        int correctState = 2;
        //Mock
        var oldLengthValue = sut.getPendingAnswers(campaignId, questionState).size();
        //Test
        sut.setPendingAnswer(new GivenAnswerDTO(questionId, participentId, campaignId, correctState, "Test"));
        //Verify
        assertEquals(oldLengthValue - 1, sut.getPendingAnswers(campaignId, questionState).size());
    }

    @Test
    void removeQuestionRemovesQuesion() throws SQLException {
        // Mock

        // Test
        sut.removeQuestion(questionId);
        // Verify
        assertEquals(amountOfQuestions - 1, sut.getAllQuestions().size());
    }

    @Test
    void getAmountOfQuestionsPerCategoryReturnsAllQuestionsFromOneCategory() throws SQLException {
        final int expectedResult = 4;

        assertEquals(expectedResult, sut.getQuestionAmountPerCategory(category));
    }

    @Test
    void getCodingQuestionThrowsNoQuestionFoundException() {
        Assertions.assertThrows(NoQuestionFoundException.class, () -> sut.getCodingQuestion(0));
    }

    @Test
    void getCodingQuestionGetCorrectQuestion() throws SQLException {
        CodingQuestionDTO result = sut.getCodingQuestion(4);
        Assertions.assertEquals("startCode", result.getCode());
        Assertions.assertEquals("testCode", result.getTest());

    }

    private QuestionDTO getOpenQuestion() {
        return new QuestionDTO(10, "dit is een test vraag", category, "open", null);
    }

    private QuestionDTO getMultipleQuestion() {
        QuestionDTO question = new QuestionDTO(10, "dit is een test vraag", category, "open", null);
        ArrayList<PossibleAnswerDTO> possibleAnswers = new ArrayList<>() {
            {
                add(new PossibleAnswerDTO("yes", 1));
                add(new PossibleAnswerDTO("no", 0));
            }
        };
        question.setPossibleAnswers(possibleAnswers);
        return question;
    }
}
