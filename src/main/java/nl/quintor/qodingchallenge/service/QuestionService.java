package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionPersistence;

import java.sql.SQLException;
import java.util.List;

public interface QuestionService {
    void setCampaignDAO(CampaignDAO campaignDAO);

    void setQuestionPersistence(QuestionPersistence questionPersistence);

    List<QuestionDTO> getQuestions(String category, int amountOfQuestions, String campaignName) throws SQLException;

    void setAnswer(QuestionCollection questionCollection) throws SQLException;
}
