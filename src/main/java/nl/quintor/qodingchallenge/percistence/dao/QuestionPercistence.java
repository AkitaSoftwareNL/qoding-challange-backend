package nl.quintor.qodingchallenge.percistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public interface QuestionPercistence {
    List<QuestionDTO> getQuestions(String category, int limit) throws SQLException;

    void setAnswer(GivenAnswerDTO answer) throws SQLException;

    String getCorrectAnswer(int questionID) throws SQLException;
}
