package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO;
    private CampaignDAO campaignDAO;

    @Autowired
    @Override
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Autowired
    @Override
    public void setQuestionDAO(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }


    @Override
    public List<QuestionDTO> getQuestions(String category, String campaignName) throws SQLException {
        if (!campaignDAO.campaignExists(campaignName))
            throw new NoCampaignFoundException(format("Campaign %s does not exist", campaignName));
        List<QuestionDTO> questions = questionDAO.getQuestions(category, campaignDAO.getAmountOfQuestions(campaignName));

        for (QuestionDTO questionDTO : questions) {
            questionDTO
                    .setPossibleAnswer(
                            questionDAO.getPossibleAnswers(
                                    questionDTO.getQuestionID()
                            )
                    );
        }

        return questions;
    }

    @Override
    public void setAnswer(QuestionCollection questionCollection) throws SQLException {
        final int CORRECT = 2;
        final int INCORRECT = 3;
        final String TYPE = "multiple";

        for (QuestionDTO question : questionCollection.getQuestions()) {
            if (question.getQuestionType().equals(TYPE)) {
                String correctAnswer = questionDAO.getCorrectAnswer(question.getQuestionID());
                if (checkAnswer(correctAnswer, question.getGivenAnswer())) {
                    question.setStateID(CORRECT);
                } else {
                    question.setStateID(INCORRECT);
                }
            }
            questionDAO.setAnswer(question, questionCollection.getCampaignName(), questionCollection.getParticipantID());
        }
    }

    private boolean checkAnswer(String correctAnswer, String givenAnswer) {
        return correctAnswer.equals(givenAnswer);
    }

    @Override
    public void createQuestion(QuestionDTO question) throws SQLException {
        questionDAO.persistQuestion(question);
    }

    @Override
    public List<QuestionDTO> getAllQuestions() throws SQLException {
        return questionDAO.getAllQuestions();
    }

    @Override
    public List<QuestionDTO> removeQuestion(int questionID) throws SQLException {
        questionDAO.removeQuestion(questionID);
        return questionDAO.getAllQuestions();
    }
}
