package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
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


    @Override
    public List<QuestionDTO> getQuestions(String category, int limit) throws SQLException {
        List<QuestionDTO> questions;
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT questionid, CATEGORY_NAME, question, QUESTION_TYPE, attachment FROM Question WHERE category_name = ? AND state != 0 ORDER BY RAND() LIMIT ?;");
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
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS FROM multiple_choice_question WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                possibleAnswers.add(new PossibleAnswerDTO(
                        resultSet.getString(1), 0));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return possibleAnswers;
    }

    @Override
    public void setAnswer(QuestionDTO question, int campaignId, int participantID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO given_answer VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, question.getQuestionID());
            statement.setInt(2, participantID);
            statement.setInt(3, campaignId);
            statement.setInt(4, question.getStateID());
            statement.setString(5, question.getGivenAnswer());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getCorrectAnswer(int questionID) throws SQLException {
        Optional<String> correctAnswer = Optional.empty();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT answer_options  FROM multiple_choice_question WHERE questionID = ? AND is_correct = 1");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                correctAnswer = Optional.ofNullable(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return correctAnswer.orElseThrow(() -> new AnswerNotFoundException(
                "No correct answer could be found",
                format("For the question with questionID: %d was no correct answer found", questionID),
                "Contact Quintor to solve this issue"
        ));
    }

    @Override
    public void persistOpenQuestion(QuestionDTO question) throws SQLException {
        final String JAVA = "JAVA";
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("insert into question(category_name, question, question_type, attachment) values (?, ?, ?, ?)");
            statement.setString(1, JAVA);
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getQuestionType());
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
            PreparedStatement statement = connection.prepareStatement("SELECT questionid, CATEGORY_NAME, question, QUESTION_TYPE, attachment FROM question WHERE STATE = 1");
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
                    new QuestionDTO(
                            resultSet.getInt(1),
                            resultSet.getString(3),
                            resultSet.getString(2),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    ));
        }
        return questions;
    }

    @Override
    public List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException {
        List<GivenAnswerDTO> givenAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("select * from given_answer where CAMPAIGN_ID = ? and STATEID = ?");
            statement.setInt(1, campaignId);
            statement.setInt(2, questionState);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                givenAnswers.add(new GivenAnswerDTO(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)));
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
            PreparedStatement statement = connection.prepareStatement("select * from question where QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                question.setQuestionID(resultSet.getInt(1));
                question.setQuestionType(resultSet.getString(2));
                question.setQuestion(resultSet.getString(3));
                question.setAttachment(resultSet.getString(4));
                question.setGivenAnswer(resultSet.getString(5));
                question.setStateID(resultSet.getInt(6));
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
        return question;
    }

    @Override
    public void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("update given_answer set STATEID = ? where QUESTIONID = ? and CAMPAIGN_ID = ? and PARTICIPANTID = ?");
            statement.setInt(1, givenAnswerDTO.getStateId());
            statement.setInt(2, givenAnswerDTO.getQuestionId());
            statement.setInt(3, givenAnswerDTO.getCampaignId());
            statement.setInt(4, givenAnswerDTO.getParticipentId());
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
            statement.setString(3, question.getQuestionType());
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

    public int getQuestionAmountPerCategory(String category) throws SQLException {
        try (
                Connection connection = getConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as amount FROM question where CATEGORY_NAME = ? AND state = 1");
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("amount");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}


