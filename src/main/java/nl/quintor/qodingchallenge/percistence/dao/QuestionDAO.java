package nl.quintor.qodingchallenge.percistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.percistence.connection.ConnectionPoolFactory.getConnection;

public class QuestionDAO {

    public List<QuestionDTO> getQuestions(String category, int limit) throws SQLException {
            List<QuestionDTO> questions = new ArrayList<>();
            try (
                    Connection connection = getConnection()
            ) {
                PreparedStatement statement = connection.prepareStatement("SELECT questionid, category_name, question, QUESTION_TYPE FROM Question WHERE category_name = ? AND state != 0 ORDER BY RAND() LIMIT ?;");
                statement.setString(1, category);
                statement.setInt(2, limit);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
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
    }

