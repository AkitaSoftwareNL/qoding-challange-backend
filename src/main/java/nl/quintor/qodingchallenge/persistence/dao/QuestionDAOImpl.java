package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.dto.builder.QuestionDTOBuilder;
import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.service.QuestionType;
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
    public List<QuestionDTO> getQuestions(String category, AmountOfQuestionTypeCollection limit) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        System.out.println(limit);
        try (
                Connection connection = getConnection()
        ) {
            for (AmountOfQuestionTypeDTO questionType : limit.collection) {
                PreparedStatement statement;
                int total = questionType.amount - (limit.getTotal() - limit.getAmount("total"));
                if (questionType.type.equalsIgnoreCase("total") && total > 0) {
                    statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE CATEGORY_NAME = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
                } else {
                    statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE CATEGORY_NAME = ? AND TYPE = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
                    statement.setInt(2, total);
                    statement.setString(2, questionType.type);
                    statement.setInt(3, questionType.amount);
                }
                statement.setString(1, category);
                questions.addAll(createQuestionDTO(statement));

            }
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
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS FROM MULTIPLE_CHOICE_QUESTION WHERE QUESTIONID = ?");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GIVEN_ANSWER_STATE VALUES (?, ?, ? ,?)");
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER) VALUES (?, ?, ?, ? )");

            statement.setInt(1, question.getQuestionID());
            statement.setString(2, participantID);
            statement.setInt(3, campaignId);
            statement.setInt(4, question.getStateID());

            statement1.setInt(1, question.getQuestionID());
            statement1.setString(2, participantID);
            statement1.setInt(3, campaignId);
            statement1.setString(4, question.getGivenAnswer()[0]);

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
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS, IS_CORRECT FROM MULTIPLE_CHOICE_QUESTION WHERE QUESTIONID = ?");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO QUESTION (CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT) VALUES (?, ?, ?, ?)");
            statement.setString(1, JAVA);
            statement.setString(2, question.getQuestion());
            statement.setInt(3, QuestionType.getEnumAsInt(question.getQuestionType()));
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
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE on QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE STATE = 1");
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
            PreparedStatement statement = connection.prepareStatement("UPDATE QUESTION SET STATE = 0 WHERE QUESTIONID = ?");
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
                                questionDTOBuilder.questionType = resultSet.getString("TYPE");
                                questionDTOBuilder.attachment = resultSet.getString("attachment");
                                try {
                                    questionDTOBuilder.startCode = getCodingQuestion(id).getCode();
                                } catch (NoQuestionFoundException e) {
                                    LOGGER.info("No startcode has been found");
//                                    LOGGER.debug(e.getMessage(), e);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM QUESTION JOIN QUESTION_TYPE on QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new QuestionDTOBuilder().with(questionDTOBuilder -> {
                            questionDTOBuilder.questionID = resultSet.getInt("QUESTIONID");
                            questionDTOBuilder.categoryType = resultSet.getString("CATEGORY_NAME");
                            questionDTOBuilder.question = resultSet.getString("QUESTION");
                            questionDTOBuilder.stateID = resultSet.getInt("STATE");
                            questionDTOBuilder.questionType = resultSet.getString("TYPE");
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
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, STARTCODE, TESTCODE FROM PROGRAMMING_QUESTION WHERE QUESTIONID = ?");
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
            PreparedStatement statement = connection.prepareStatement("UPDATE GIVEN_ANSWER_STATE\n" +
                    "SET STATEID = ? \n" +
                    "WHERE QUESTIONID = ?  AND CAMPAIGN_ID = ? AND PARTICIPANTID = ?");
            statement.setInt(1, givenAnswerDTO.getStateId());
            statement.setInt(2, givenAnswerDTO.getQuestionId());
            statement.setInt(3, givenAnswerDTO.getCampaignId());
            statement.setString(4, givenAnswerDTO.getParticipantId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void persistMultipleQuestion(QuestionDTO question) throws SQLException {
        final String delimiter = "&";
        List<String> possibleAnswersString = makeString(question.getPossibleAnswers(), delimiter);
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("CALL SP_MultipleChoiceQuestion(?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, "JAVA");
            statement.setString(2, question.getQuestion());
            statement.setInt(3, QuestionType.getEnumAsInt(question.getQuestionType()));
            statement.setString(4, question.getAttachment());
            statement.setString(5, possibleAnswersString.get(0));
            statement.setString(6, possibleAnswersString.get(1));
            statement.setInt(7, question.getPossibleAnswers().size());
            statement.setString(8, delimiter);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private List<String> makeString(List<PossibleAnswerDTO> possibleAnswers, String delimiter) {
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS AMOUNT FROM QUESTION WHERE CATEGORY_NAME = ? AND STATE = 1");
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AMOUNT");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public AmountOfQuestionTypeCollection countQuestions() throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS AMOUNT , QUESTION_TYPE.TYPE AS TYPE FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE STATE = 1 GROUP BY QUESTION.QUESTION_TYPE;");
            ResultSet resultSet = statement.executeQuery();
            var amounts = new ArrayList<AmountOfQuestionTypeDTO>();
            while (resultSet.next()) {
                amounts.add(new AmountOfQuestionTypeDTO(resultSet));
            }

            int total = 0;
            for (var amountType : amounts) {
                total += amountType.amount;
            }

            amounts.add(new AmountOfQuestionTypeDTO("total", total));

            return new AmountOfQuestionTypeCollection(amounts);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}


