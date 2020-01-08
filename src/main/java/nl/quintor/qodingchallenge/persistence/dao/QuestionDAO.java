package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QuestionDAO {
    List<QuestionDTO> getQuestions(String category, AmountOfQuestionTypeCollection limit) throws SQLException;

    void setAnswer(QuestionDTO question, int campaignId, String participantID) throws SQLException;

    ArrayList<PossibleAnswerDTO> getCorrectAnswers(int questionID) throws SQLException;

    List<PossibleAnswerDTO> getPossibleAnswers(int questionID) throws SQLException;

    void persistOpenQuestion(QuestionDTO question) throws SQLException;

    List<QuestionDTO> getAllQuestions() throws SQLException;

    void removeQuestion(int questionID) throws SQLException;

    void persistMultipleQuestion(QuestionDTO question) throws SQLException;

    List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException;

    QuestionDTO getQuestion(int questionid) throws SQLException;

    CodingQuestionDTO getCodingQuestion(int id) throws SQLException;

    void setPendingAnswer(GivenAnswerDTO questionDTO) throws SQLException;

    int getQuestionAmountPerCategory(String category) throws SQLException;

    int countQuestions() throws SQLException;
}
