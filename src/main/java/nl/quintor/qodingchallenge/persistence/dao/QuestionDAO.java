package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDAO {
    List<QuestionDTO> getQuestions(String category, int limit) throws SQLException;

    void setAnswer(QuestionDTO question, String campaignName, int participantID) throws SQLException;

    String getCorrectAnswer(int questionID) throws SQLException;

    List<String> getPossibleAnswers(int questionID) throws SQLException;

    void persistQuestion(QuestionDTO question) throws SQLException;

    List<QuestionDTO> getAllQuestions() throws SQLException;

    void removeQuestion(int questionID) throws SQLException;
}
