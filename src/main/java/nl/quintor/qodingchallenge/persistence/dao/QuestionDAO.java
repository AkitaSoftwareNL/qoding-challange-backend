package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDAO {
    List<QuestionDTO> getQuestions(String category, int limit) throws SQLException;

    void setAnswer(QuestionDTO question, String campaignName, int participantID) throws SQLException;

    String getCorrectAnswer(int questionID) throws SQLException;

    List<String> getPossibleAnswers(int questionID) throws SQLException;

    void persistQuestion(QuestionDTO question) throws SQLException;

    List<QuestionDTO> getAllQuestions() throws SQLException;

    List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException;

    QuestionDTO getQuestion(int questionid) throws SQLException, NoQuestionFoundException;

    void setPendingAnswer(GivenAnswerDTO questionDTO) throws SQLException;
}
