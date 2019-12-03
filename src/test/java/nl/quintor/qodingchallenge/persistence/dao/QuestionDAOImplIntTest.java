package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class QuestionDAOImplIntTest {

    private final int QUESTION_ID = 3;
    private final QuestionDTO questionDTO = new QuestionDTO(10, "dit is een test vraag", "open", null);
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
        List<String> possibleAnswers = sut.getPossibleAnswers(QUESTION_ID);

        int AMOUNT_OF_ANSWERS = 2;
        assertEquals(AMOUNT_OF_ANSWERS, possibleAnswers.size());
    }

    @Test
    void setAnswerAddsOneMoreAnswerToQuestion() {
        assertDoesNotThrow(() -> sut.setAnswer(questionDTO, "testcampaign", 1));
    }

    @Test
    void getCorrectAnswerGivesAllCorrectAnswers() throws SQLException {
        String actualResult = sut.getCorrectAnswer(QUESTION_ID);

        assertFalse(actualResult.isEmpty());
    }

}
