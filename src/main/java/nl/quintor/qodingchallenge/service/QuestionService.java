package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;

import java.sql.SQLException;
import java.util.List;

public interface QuestionService {
    void setCampaignDAO(CampaignDAO campaignDAO);

    void setQuestionDAO(QuestionDAO questionDAO);

    List<QuestionDTO> getQuestions(String category, String campaignName) throws SQLException;

    void setAnswer(QuestionCollection questionCollection) throws SQLException;

    void createQuestion(QuestionDTO question) throws SQLException;

    List<QuestionDTO> getAllQuestions() throws SQLException;

    List<QuestionDTO> removeQuestion(int questionID) throws SQLException;

    List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException;

    QuestionDTO getQuestion(int questionid) throws SQLException, NoQuestionFoundException;

    void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) throws SQLException;
}
