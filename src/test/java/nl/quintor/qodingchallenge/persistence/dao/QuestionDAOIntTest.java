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
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class QuestionDAOIntTest {

    private final int AMOUNT_OF_QUESTIONS = 3;
    private final String CATEGORY = "JAVA";
    private final int AMOUNT_OF_ANSWERS = 2;
    private final int QUESTION_ID = 3;
    private QuestionDAO sut;
    private List<QuestionDTO> questionDTOList;
    private List<String> possibleAnswers;
    private QuestionDTO questionDTO = new QuestionDTO(10, "dit is een test vraag", "open", null);

    @BeforeEach
    void setUp() {
        this.sut = new QuestionDAO();
        try {
            Connection connection = getConnection();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testQuestionDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getQuestionsReturnsQuestions() throws SQLException {
        questionDTOList = sut.getQuestions(CATEGORY, AMOUNT_OF_QUESTIONS);

        assertEquals(AMOUNT_OF_QUESTIONS, questionDTOList.size());
    }

    @Test
    void getPossibleAnswerReturnsPossibleAnswers() throws SQLException {
        possibleAnswers = sut.getPossibleAnswers(QUESTION_ID);

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