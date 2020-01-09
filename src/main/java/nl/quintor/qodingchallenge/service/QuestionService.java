package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public interface QuestionService {
    void setCampaignDAO(CampaignDAO campaignDAO);

    void setQuestionDAO(QuestionDAO questionDAO);

    @Autowired
    void setParticipantDAO(ParticipantDAO participantDAO);

    QuestionCollection getQuestions(String category, int campaignID) throws SQLException;

    void setAnswer(QuestionCollection questionCollection) throws SQLException;

    void createQuestion(QuestionDTO question) throws SQLException;

    List<QuestionDTO> getAllQuestions() throws SQLException;

    void removeQuestion(int questionID) throws SQLException;

    List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException;

    QuestionDTO getQuestion(int questionid) throws SQLException;

    void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) throws SQLException;

    AmountOfQuestionTypeCollection countQuestions() throws SQLException;
}
