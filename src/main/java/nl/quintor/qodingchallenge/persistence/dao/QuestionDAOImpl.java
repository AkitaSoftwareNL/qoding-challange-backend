package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.builder.QuestionDTOBuilder;
import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class QuestionDAOImpl implements QuestionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionDAOImpl.class);


    @Override
    public List<QuestionDTO> getQuestions(String category, int limit) throws SQLException {
        List<QuestionDTO> questions;
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT FROM question WHERE CATEGORY_NAME = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
            statement.setString(1, category);
            statement.setInt(2, limit);
            questions = createQuestionDTO(statement);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return questions;
    }

    @Override
    public List<PossibleAnswerDTO> getPossibleAnswers(int questionID) throws SQLException {
        List<PossibleAnswerDTO> possibleAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS FROM multiple_choice_question WHERE QUESTIONID = ? ORDER BY RAND()");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                possibleAnswers.add(new PossibleAnswerDTO(
                        resultSet.getString("ANSWER_OPTIONS"), 0));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return possibleAnswers;
    }

    @Override
    public void setAnswer(QuestionDTO question, int campaignId, String participantID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO given_answer_state VALUES (?, ?, ? ,?)");
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO given_answer (questionID, PARTICIPANTID, campaign_ID, given_answer) VALUES (?, ?, ?, ? )");

            statement.setInt(1, question.getQuestionID());
            statement.setString(2, participantID);
            statement.setInt(3, campaignId);
            statement.setInt(4, question.getStateID());

            statement1.setInt(1, question.getQuestionID());
            statement1.setString(2, participantID);
            statement1.setInt(3, campaignId);
            statement1.setString(4, question.getGivenAnswers()[0]);

            statement.executeUpdate();
            statement1.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ArrayList<PossibleAnswerDTO> getCorrectAnswers(int questionID) throws SQLException {
        ArrayList<PossibleAnswerDTO> correctAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS, IS_CORRECT FROM multiple_choice_question WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                correctAnswers.add(
                        new PossibleAnswerDTO(
                                resultSet.getString("ANSWER_OPTIONS"),
                                resultSet.getInt("IS_CORRECT")
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        if (correctAnswers.isEmpty()) {
            throw new AnswerNotFoundException(
                    "No correct answer could be found",
                    format("For the question with questionID: %d was no correct answer found", questionID),
                    "Please contact support to solve this issue"
            );
        }
        return correctAnswers;
    }

    @Override
    public void persistOpenQuestion(QuestionDTO question) throws SQLException {
        final String JAVA = "JAVA";
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO question (CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT) VALUES (?, ?, ?, ?)");
            statement.setString(1, JAVA);
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getQuestionType().toLowerCase());
            statement.setString(4, question.getAttachment());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<QuestionDTO> getAllQuestions() throws SQLException {
        List<QuestionDTO> questions;
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT FROM question WHERE STATE = 1");
            questions = createQuestionDTO(statement);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return questions;
    }

    @Override
    public void removeQuestion(int questionID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("UPDATE question SET STATE = 0 WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private List<QuestionDTO> createQuestionDTO(PreparedStatement statement) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            questions.add(
                    new QuestionDTOBuilder().with(questionDTOBuilder -> {
                                int id = resultSet.getInt("QUESTIONID");
                                questionDTOBuilder.questionID = id;
                                questionDTOBuilder.categoryType = resultSet.getString("CATEGORY_NAME");
                                questionDTOBuilder.question = resultSet.getString("QUESTION");
                                questionDTOBuilder.questionType = resultSet.getString("QUESTION_TYPE");
                        questionDTOBuilder.attachment = resultSet.getString("ATTACHMENT");
                                try {
                                    questionDTOBuilder.startCode = getCodingQuestion(id).getCode();
                                } catch (NoQuestionFoundException e) {
                                    LOGGER.info("No startcode has been found");
                                    LOGGER.debug(e.getMessage(), e);
                                    questionDTOBuilder.startCode = "";
                                }
                            }
                    ).build()
            );
        }
        return questions;
    }

    @Override
    public List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException {
        List<GivenAnswerDTO> givenAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ga.QUESTIONID, ga.PARTICIPANTID, ga.CAMPAIGN_ID, ga.GIVEN_ANSWER, gas.STATEID FROM given_answer as ga inner join given_answer_state as gas\n" +
                    "on ga.QUESTIONID = gas.QUESTIONID AND ga.CAMPAIGN_ID = gas.CAMPAIGN_ID AND ga.PARTICIPANTID = gas.PARTICIPANTID\n" +
                    "where ga.CAMPAIGN_ID = ? AND gas.STATEID = ?;");
            statement.setInt(1, campaignId);
            statement.setInt(2, questionState);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                givenAnswers.add(new GivenAnswerDTO(
                        resultSet.getInt("QUESTIONID"),
                        resultSet.getString("PARTICIPANTID"),
                        resultSet.getInt("CAMPAIGN_ID"),
                        resultSet.getInt("STATEID"),
                        resultSet.getString("GIVEN_ANSWER")));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return givenAnswers;
    }

    @Override
    public QuestionDTO getQuestion(int questionID) throws SQLException {
        QuestionDTO question = new QuestionDTO();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM question WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new QuestionDTOBuilder().with(questionDTOBuilder -> {
                            questionDTOBuilder.questionID = resultSet.getInt("QUESTIONID");
                            questionDTOBuilder.categoryType = resultSet.getString("CATEGORY_NAME");
                            questionDTOBuilder.question = resultSet.getString("QUESTION");
                            questionDTOBuilder.stateID = resultSet.getInt("STATE");
                            questionDTOBuilder.questionType = resultSet.getString("QUESTION_TYPE");
                            questionDTOBuilder.attachment = resultSet.getString("ATTACHMENT");
                        }
                ).build();
            } else {
                throw new NoQuestionFoundException(
                        "No question has been found",
                        format("Question with questionID %d was not found", questionID),
                        "Try an different questionID"
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public CodingQuestionDTO getCodingQuestion(int id) throws SQLException {
        Optional<CodingQuestionDTO> codingQuestionDTO = Optional.empty();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("select QUESTIONID, STARTCODE, TESTCODE from PROGRAMMING_QUESTION WHERE QUESTIONID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                codingQuestionDTO = Optional.of(new CodingQuestionDTO(
                        resultSet.getString("STARTCODE"),
                        resultSet.getString("TESTCODE")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return codingQuestionDTO.orElseThrow(() -> {
            throw new NoQuestionFoundException(
                    "No question has been found",
                    format("Question with questionID %d was not found", id),
                    "Try an different questionID"
            );
        });

    }

    @Override
    public void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("UPDATE given_answer_state\n" +
                    "SET STATEID = ? \n" +
                    "WHERE QUESTIONID = ?  AND campaign_ID = ? AND PARTICIPANTID = ?");
            statement.setInt(1, givenAnswerDTO.getStateId());
            statement.setInt(2, givenAnswerDTO.getQuestionId());
            statement.setInt(3, givenAnswerDTO.getCampaignId());
            statement.setString(4, givenAnswerDTO.getParticipantId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * <p>Sets the question with corresponding values in the database.
     *
     * @param question question to be inserted.
     * @throws SQLException if connection fails, and when data cannot be added to the database.
     * @rollback When an exception occurs all the data that was previously added in this scope will be reverted.
     */
    @Override
    public void persistMultipleQuestion(QuestionDTO question) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            insertQuestion(connection, question);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void insertQuestion(Connection connection, QuestionDTO question) throws SQLException {
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO question(category_name, question, question_type, attachment) values (?, ?, ?, ?);");
            PreparedStatement statementMultiple = connection.prepareStatement("INSERT INTO multiple_choice_question(QUESTIONID, ANSWER_OPTIONS, IS_CORRECT) values (?, ?, ?);");

            statement.setString(1, "JAVA");
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getQuestionType().toLowerCase());
            statement.setString(4, question.getAttachment());

            statement.executeUpdate();

            int questionID = getQuestionID(connection, question.getQuestion());

            statementMultiple.setInt(1, questionID);

            for (PossibleAnswerDTO possibleAnswer : question.getPossibleAnswers()) {
                statementMultiple.setString(2, possibleAnswer.getPossibleAnswer());
                statementMultiple.setInt(3, possibleAnswer.getIsCorrect());
                statementMultiple.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e);
        }
    }

    private int getQuestionID(Connection connection, String question) throws SQLException {
        Optional<Integer> questionID = Optional.empty();
        PreparedStatement statement = connection.prepareStatement("SELECT question.QUESTIONID FROM question WHERE QUESTION = ?");
        statement.setString(1, question);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            questionID = Optional.of(resultSet.getInt("QUESTIONID"));
        }
        return questionID.orElseThrow(() -> new NoQuestionFoundException(
                "QuestionID not found",
                format("QuestionID from question %s has not been found", question),
                "The question is most likely not valid, try a different question"
        ));
    }

    @Deprecated
    public List<String> makeString(List<PossibleAnswerDTO> possibleAnswers, String delimiter) {
        List<String> possibleAnswersString = new ArrayList<>();
        String possibleAnswerString = delimiter;
        String isCorrectString = delimiter;

        for (PossibleAnswerDTO possibleAnswer : possibleAnswers) {
            possibleAnswerString = possibleAnswerString.concat(possibleAnswer.getPossibleAnswer() + delimiter);
            isCorrectString = isCorrectString.concat(possibleAnswer.getIsCorrect() + delimiter);
        }

        possibleAnswersString.add(possibleAnswerString);
        possibleAnswersString.add(isCorrectString);

        return possibleAnswersString;
    }

    @Override
    public int getQuestionAmountPerCategory(String category) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS AMOUNT FROM question WHERE CATEGORY_NAME = ? AND STATE = 1");
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AMOUNT");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int countQuestions() throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS AMOUNT FROM question WHERE STATE = 1");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AMOUNT");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * @return true when there are multiple correct answers
     */
    @Override
    public boolean getAmountOfRightAnswersPerQuestion(int questionID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(IS_CORRECT) as CORRECT FROM multiple_choice_question WHERE IS_CORRECT = 1 AND QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("CORRECT") > 1;
        }
    }

    @Override
    public synchronized void persistProgramQuestion(QuestionDTO question) throws SQLException {
        final String JAVA = "JAVA";
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement insertQuestion = connection.prepareStatement("INSERT INTO question (CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT) VALUES (?, ?, ?, ?)");
            insertQuestion.setString(1, JAVA);
            insertQuestion.setString(2, question.getQuestion());
            insertQuestion.setString(3, question.getQuestionType().toLowerCase());
            insertQuestion.setString(4, question.getAttachment());
            insertQuestion.executeUpdate();


            PreparedStatement selectStatement = connection.prepareStatement("SELECT QUESTIONID FROM question ORDER BY QUESTIONID DESC");
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int questionID = resultSet.getInt("QUESTIONID");


            PreparedStatement insertProgramming = connection.prepareStatement("INSERT INTO programming_question (QUESTIONID, STARTCODE, TESTCODE) VALUES (?, ?, ?)");
            insertProgramming.setInt(1, questionID);
            insertProgramming.setString(2, question.getStartCode());
            insertProgramming.setString(3, question.getUnitTest());
            insertProgramming.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}


