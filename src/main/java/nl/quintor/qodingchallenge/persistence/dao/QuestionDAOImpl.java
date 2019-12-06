package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class QuestionDAOImpl implements QuestionDAO {


    @Override
    public List<QuestionDTO> getQuestions(String category, int limit) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT questionid, question, QUESTION_TYPE, attachment FROM Question WHERE category_name = ? AND state != 0 ORDER BY RAND() LIMIT ?;");
            statement.setString(1, category);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(new QuestionDTO(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return questions;
    }

    @Override
    public List<String> getPossibleAnswers(int questionID) throws SQLException {
        List<String> possibleAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS FROM multiple_choice_question WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                possibleAnswers.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return possibleAnswers;
    }

    @Override
    public void setAnswer(QuestionDTO question, String campaignName, int participantID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO given_answer VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, question.getQuestionID());
            statement.setInt(2, participantID);
            statement.setString(3, campaignName);
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
        return correctAnswer.orElseThrow(AnswerNotFoundException::new);
    }

    @Override
    public void persistQuestion(QuestionDTO question) throws SQLException {
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
        List<QuestionDTO> questions = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM question");
            statement.executeQuery();
        } catch (SQLException e) {
            throw new SQLException(e);
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

            while (resultSet.next()){
                GivenAnswerDTO givenAnswerDTO = new GivenAnswerDTO();

                givenAnswerDTO.setQuestionId(resultSet.getInt(1));
                givenAnswerDTO.setParticipentId(resultSet.getInt(2));
                givenAnswerDTO.setCampaignId(resultSet.getInt(3));
                givenAnswerDTO.setStateId(resultSet.getInt(4));
                givenAnswerDTO.setGivenAnswer(resultSet.getString(5));

                givenAnswers.add(givenAnswerDTO);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return givenAnswers;
    }

    @Override
    public QuestionDTO getQuestion(int questionid) throws SQLException {
        QuestionDTO question = new QuestionDTO();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("select * from question where questionid = ?");
            statement.setInt(1, questionid);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            question.setQuestionID(resultSet.getInt(1));
            question.setQuestion(resultSet.getString(2));
            question.setQuestionType(resultSet.getString(3));
            question.setAttachment(resultSet.getString(4));
            question.setGivenAnswer(resultSet.getString(5));
            question.setStateID(resultSet.getInt(6));

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
}


