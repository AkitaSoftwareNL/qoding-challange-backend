package nl.quintor.qodingchallenge.percistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public interface IQuestionPercistence {
    List<QuestionDTO> getQuestions(String category, int limit) throws SQLException;
}
