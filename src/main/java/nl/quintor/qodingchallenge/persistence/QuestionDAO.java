package nl.quintor.qodingchallenge.persistence;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;

import java.sql.SQLException;

public interface QuestionDAO {
    String getCorrectAnswer(int questionID) throws SQLException;

    void persistAnswer(GivenAnswerDTO givenAnswer);
}
