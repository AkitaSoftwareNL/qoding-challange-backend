package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import org.h2.tools.RunScript;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

class QuestionDAOImplIntTest {

    private final int questionId = 3;
    private final int amountOfQuestions = 3;
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
        final String category = "JAVA";
        List<QuestionDTO> questionDTOList = sut.getQuestions(category, amountOfQuestions);

        assertEquals(amountOfQuestions, questionDTOList.size());
    }

    @Test
    void getPossibleAnswerReturnsPossibleAnswers() throws SQLException {
        List<PossibleAnswerDTO> possibleAnswers = sut.getPossibleAnswers(questionId);

        int AMOUNT_OF_ANSWERS = 2;
        assertEquals(AMOUNT_OF_ANSWERS, possibleAnswers.size());
    }

    @Test
    void setAnswerAddsOneMoreAnswerToQuestion() {
        assertDoesNotThrow(() -> sut.setAnswer(getOpenQuestion(), "testcampaign", 1));
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

    private QuestionDTO getOpenQuestion() {
        return new QuestionDTO(10, "dit is een test vraag", "open", null);
    }

    private QuestionDTO getMultipleQuestion() {
        QuestionDTO question = new QuestionDTO(10, "dit is een test vraag", "open", null);
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
