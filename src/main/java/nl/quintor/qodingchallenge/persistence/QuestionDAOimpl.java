package nl.quintor.qodingchallenge.persistence;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionDAOimpl implements QuestionDAO {

    @Override
    public String getCorrectAnswer(int questionID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT answer_option FROM multiple_choice_question WHERE questionID = ? AND is_correct = 1");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }


    @Override
    public void persistAnswer(GivenAnswerDTO givenAnswer) {

    }
}
