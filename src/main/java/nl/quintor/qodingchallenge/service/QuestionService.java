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

    QuestionCollection getQuestions(String category, int campaignID);

    void setAnswer(QuestionCollection questionCollection) throws SQLException;

    void createQuestion(QuestionDTO question);

    List<QuestionDTO> getAllQuestions();

    void removeQuestion(int questionID);

    List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState);

    QuestionDTO getQuestion(int questionid);

    void setPendingAnswer(GivenAnswerDTO givenAnswerDTO);

    AmountOfQuestionTypeCollection countQuestions();
}
