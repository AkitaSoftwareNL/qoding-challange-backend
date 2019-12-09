package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;

public interface QuestionDAO {

    void persistMultipleQuestion(QuestionDTO question) throws SQLException;

}
