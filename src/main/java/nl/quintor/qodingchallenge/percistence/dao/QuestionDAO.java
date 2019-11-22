package nl.quintor.qodingchallenge.percistence.dao;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.percistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class QuestionDAO implements IQuestionPercistence {


    @Override
    public List<QuestionDTO> getQuestions(String category, int limit) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT questionid, category_name, question, QUESTION_TYPE FROM Question WHERE category_name = ? AND state != 0 ORDER BY RAND() LIMIT ?;");
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
    public void setAnswer(GivenAnswerDTO answer) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO given_answer VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, answer.getQuestionID());
            statement.setInt(2, answer.getParticipantID());
            statement.setString(3, answer.getCampaignName());
            statement.setInt(4, answer.getStateID());
            statement.setString(5, answer.getGivenAnswer());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
}


