package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDAO {

    List<QuestionDTO> getAllQuestions() throws SQLException;

    void persistMultipleQuestion(QuestionDTO question) throws SQLException;

}
