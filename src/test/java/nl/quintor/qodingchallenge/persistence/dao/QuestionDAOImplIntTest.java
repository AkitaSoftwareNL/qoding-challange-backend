package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.dto.builder.QuestionDTOBuilder;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.service.QuestionType;
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
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionDAOImplIntTest {

    private final int campaignId = 1;
    private final int questionState = 1;
    private final int questionId = 3;
    private final int amountOfQuestions = 15;
    private final String participantId = "1452950a-8059-4bd1-b397-d2bd765d6b9b";
    private final String category = "JAVA";

    private QuestionDAOImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new QuestionDAOImpl();
        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
            inputStream = getClass().getClassLoader().getResourceAsStream("DLL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getQuestionsReturnsQuestionsWithALimit() throws SQLException {
        AmountOfQuestionTypeCollection collection = new AmountOfQuestionTypeCollection();
        ArrayList<AmountOfQuestionTypeDTO> list = new ArrayList<>();
        list.add(new AmountOfQuestionTypeDTO(QuestionType.OPEN.toString(), 2));
        list.add(new AmountOfQuestionTypeDTO(QuestionType.TOTAL.toString(), 3));
        collection.setCollection(list);
        assertEquals(5, sut.getQuestions(category, collection).size());
    }

    @Deprecated
    void getQuestionsReturnsQuestionsWithALimitDeprecated() throws SQLException {
        AmountOfQuestionTypeCollection collection = new AmountOfQuestionTypeCollection();
        ArrayList<AmountOfQuestionTypeDTO> list = new ArrayList<>();
        list.add(new AmountOfQuestionTypeDTO(QuestionType.OPEN.toString(), 2));
        list.add(new AmountOfQuestionTypeDTO(QuestionType.TOTAL.toString(), 3));
        collection.setCollection(list);
        assertEquals(3, sut.getQuestionsDeprecated(category, collection).size());
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

        var q = getQuestion();
        q.setQuestionID(1);

        sut.setAnswer(q, 1, participantId);

        assertEquals(oldLength + 1, sut.getPendingAnswers(campaignId, questionState).size());
    }

    @Test
    void getCorrectAnswerGivesAllCorrectAnswers() throws SQLException {
        PossibleAnswerDTO actualResult = sut.getCorrectAnswers(questionId).get(0);

        assertNotNull(actualResult);
    }

    @Test
    void persistOpenQuestionPersistsOpenQuestion() throws SQLException {
        // Mock

        // Test
        sut.persistOpenQuestion(getQuestion());
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
    @Deprecated
    void makeStringReturnsOneString() throws SQLException {
        List<PossibleAnswerDTO> possibleAnswerDTOS = getMultipleQuestion().getPossibleAnswers();
        String delimeter = ",";

        var result = sut.makeString(possibleAnswerDTOS, delimeter);
        assertFalse(result.contains(","));
    }

    @Test
    void getPendingAnswersReturnPendingAnswers() throws SQLException {
        int expectedLength = 75;
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
        final int falseId = 50;
        //Mock

        //Test

        //Verify
        assertThrows(NoQuestionFoundException.class, () -> sut.getQuestion(falseId));
    }

    @Test
    void setPendingAnswerAddAmountPlusOne() throws SQLException {
        final int correctState = 2;
        //Mock
        var oldLengthValue = sut.getPendingAnswers(campaignId, questionState).size();
        //Test
        sut.setPendingAnswer(new GivenAnswerDTO(questionId, participantId, campaignId, correctState, "Test"));
        //Verify
        assertEquals(oldLengthValue - 1, sut.getPendingAnswers(campaignId, questionState).size());
    }

    @Test
    void removeQuestionRemovesQuestion() throws SQLException {
        // Mock

        // Test
        sut.removeQuestion(questionId);
        // Verify
        assertEquals(amountOfQuestions - 1, sut.getAllQuestions().size());
    }

    @Deprecated
    void getAmountOfQuestionsPerCategoryReturnsAllQuestionsFromOneCategory() throws SQLException {
        final int expectedResult = 15;

        assertEquals(expectedResult, sut.getQuestionAmountPerCategory(category));
    }

    @Test
    void getCodingQuestionThrowsNoQuestionFoundException() {
        Assertions.assertThrows(NoQuestionFoundException.class, () -> sut.getCodingQuestion(0));
    }

    @Test
    void getCodingQuestionGetCorrectQuestion() throws SQLException {
        CodingQuestionDTO result = sut.getCodingQuestion(13);
        Assertions.assertEquals("startCode", result.getCode());
        Assertions.assertEquals("testCode", result.getTest());

    }

    @Test
    void countQuestionsGetsRightAmountOfQuestions() throws SQLException {
        var expectedAmount = new ArrayList<AmountOfQuestionTypeDTO>();
        expectedAmount.add(new AmountOfQuestionTypeDTO("open", 8));
        expectedAmount.add(new AmountOfQuestionTypeDTO("multiple", 5));
        expectedAmount.add(new AmountOfQuestionTypeDTO("program", 2));
        expectedAmount.add(new AmountOfQuestionTypeDTO("total", 15));
        // Mock

        // Test
        AmountOfQuestionTypeCollection actualAmount = sut.countQuestions();
        // Verify
        assertEquals(expectedAmount, actualAmount.getCollection());
    }

    @Test
    void getAmountOfRightAnswersPerQuestionReturnsFalseWhenOneAnswerIsCorrect() throws SQLException {
        final boolean expectedResult = false;

        assertEquals(expectedResult, sut.getAmountOfRightAnswersPerQuestion(questionId));
    }

    @Test
    void getAmountOfRightAnswersPerQuestionReturnsTrueWhenMultipleAnswerIsCorrect() throws SQLException {
        final int testQuestionID = 15;
        final boolean expectedResult = true;

        assertEquals(expectedResult, sut.getAmountOfRightAnswersPerQuestion(testQuestionID));
    }

    @Test
    void persistProgramQuestionPersistsProgramQuestion() throws SQLException {
        // Mock

        // Test
        sut.persistProgramQuestion(getQuestion());
        // Verify
        assertEquals(amountOfQuestions + 1, sut.getAllQuestions().size());
    }

    @Test
    void persistMultipleQuestionPersistMultipleChoiceQuestion() throws SQLException {
        // Mock

        // Test
        sut.persistMultipleQuestion(getMultipleQuestion());
        // Verify
        assertEquals(amountOfQuestions + 1, sut.getAllQuestions().size());
    }



    private QuestionDTO getQuestion() throws SQLException {
        return new QuestionDTOBuilder().with(questionDTOBuilder -> {
            questionDTOBuilder.questionID = 1;
            questionDTOBuilder.question = "Some question";
            questionDTOBuilder.categoryType = category;
            questionDTOBuilder.questionType = "open";
            questionDTOBuilder.stateID = questionState;
            questionDTOBuilder.givenAnswers = new String[]{"some answer"};
        }).build();
    }

    private QuestionDTO getMultipleQuestion() throws SQLException {
        QuestionDTO question = getQuestion();
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
