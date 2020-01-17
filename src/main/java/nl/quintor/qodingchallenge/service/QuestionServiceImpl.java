package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.exception.EmptyQuestionException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import nl.quintor.qodingchallenge.service.questionstrategy.MultipleStrategyImpl;
import nl.quintor.qodingchallenge.service.questionstrategy.OpenStrategyImpl;
import nl.quintor.qodingchallenge.service.questionstrategy.ProgramStrategyImpl;
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
    private ParticipantDAO participantDAO;
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
        strategies.add(new ProgramStrategyImpl(questionDAO));
    }

    @Override
    @Autowired
    public void setParticipantDAO(ParticipantDAO participantDAO) {
        this.participantDAO = participantDAO;
    }

    @Override
    public QuestionCollection getQuestions(String category, int campaignID) throws SQLException {
        if (!campaignDAO.campaignExists(campaignID))
            throw new NoCampaignFoundException(
                    "The campaign you tried to enter has already expired or does not exist. If none of these statements are correct please contact support.",
                    format("Campaign id = %s", campaignID),
                    "Try to use a new campaign name"
            );
        List<QuestionDTO> questions = questionDAO.getQuestions(category, campaignDAO.getAmountOfQuestions(campaignID));

        for (QuestionDTO questionDTO : questions) {
            questionDTO
                    .setPossibleAnswers(
                            questionDAO.getPossibleAnswers(
                                    questionDTO.getQuestionID()
                            )
                    );
            questionDTO.setHasMultipleAnswers(
                    questionDAO.getAmountOfRightAnswersPerQuestion(questionDTO.getQuestionID())
            );
        }
        return new QuestionCollection("1", campaignID, campaignDAO.getCampaignName(campaignID), questions)
                .shuffle();
    }

    @Override
    public void setAnswer(QuestionCollection questionCollection) throws SQLException {
        participantDAO.addTimeToParticipant(questionCollection.getParticipantID());
        for (QuestionDTO question : questionCollection.getQuestions()) {
            for (QuestionStrategy strategy : strategies) {
                if (strategy.isType(question.getQuestionType())) {
                    strategy.validateAnswer(question);
                }
            }
            questionDAO.setAnswer(question, questionCollection.getCampaignId(), questionCollection.getParticipantID());
        }
    }

    @Override
    public void createQuestion(QuestionDTO question) throws SQLException {
        if (question.getQuestion().isEmpty()) {
            throw new EmptyQuestionException(
                    "The question field cannot be empty, please enter a question",
                    "The field question can not be empty",
                    "Please put your question in the field Question"
            );
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

    @Override
    public AmountOfQuestionTypeCollection countQuestions() throws SQLException {
        return questionDAO.countQuestions();
    }
}
