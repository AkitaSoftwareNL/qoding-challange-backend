package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.exception.EmptyQuestionException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import nl.quintor.qodingchallenge.service.questionstrategy.MultipleStrategyImpl;
import nl.quintor.qodingchallenge.service.questionstrategy.OpenStrategyImpl;
import nl.quintor.qodingchallenge.service.questionstrategy.QuestionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO;
    private CampaignDAO campaignDAO;
    private List<QuestionStrategy> strategies = new ArrayList<>();

    @Override
    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    @Autowired
    public void setQuestionDAO(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
        strategies.add(new OpenStrategyImpl(questionDAO));
        strategies.add(new MultipleStrategyImpl(questionDAO));
    }

    @Override
    public QuestionCollection getQuestions(String category, String campaignName) throws SQLException {
        if (!campaignDAO.campaignExists(campaignName))
            throw new NoCampaignFoundException(format("Campaign %s does not exist", campaignName));
        List<QuestionDTO> questions = questionDAO.getQuestions(category, campaignDAO.getAmountOfQuestions(campaignName));

        for (QuestionDTO questionDTO : questions) {
            questionDTO
                    .setPossibleAnswers(
                            questionDAO.getPossibleAnswers(
                                    questionDTO.getQuestionID()
                            )
                    );
        }
        return new QuestionCollection(1, campaignDAO.getCampaignID(campaignName), campaignName, questions);
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
            questionDAO.setAnswer(question, questionCollection.getCampaignId(), questionCollection.getParticipantID());
        }
    }

    private boolean checkAnswer(String correctAnswer, String givenAnswer) {
        return correctAnswer.equals(givenAnswer);
    }

    @Override
    public void createQuestion(QuestionDTO question) throws SQLException {
        if (question.getQuestion().isEmpty()) {
            throw new EmptyQuestionException("Question can not be empty.");
        }
        String questionType = question.getQuestionType();
        for (QuestionStrategy strategy : strategies) {
            if (strategy.isType(questionType)) {
                strategy.persistQuestion(question);
                break;
            }
        }
    }

    @Override
    public List<QuestionDTO> getAllQuestions() throws SQLException {
        return questionDAO.getAllQuestions();
    }

    @Override
    public void removeQuestion(int questionID) throws SQLException {
        questionDAO.removeQuestion(questionID);
    }

    @Override
    public List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) throws SQLException {
        return questionDAO.getPendingAnswers(campaignId, questionState);
    }

    @Override
    public QuestionDTO getQuestion(int questionid) throws SQLException {
        return questionDAO.getQuestion(questionid);
    }

    @Override
    public void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) throws SQLException {
        questionDAO.setPendingAnswer(givenAnswerDTO);
    }
}
