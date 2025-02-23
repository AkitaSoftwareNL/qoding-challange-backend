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
    public QuestionCollection getQuestions(String category, int campaignID){
        if (!campaignDAO.campaignExists(campaignID))
            throw new NoCampaignFoundException(
                    "De campagne die u heeft ingevoerd bestaat niet of kan al afgelopen zijn. Als geen van deze beweringen waar is neem contact op met support",
                    format("Campagne id = %s", campaignID),
                    "Probeer een andere campagne naam"
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
    public void createQuestion(QuestionDTO question) {
        if (question.getQuestion().isEmpty()) {
            throw new EmptyQuestionException(
                    "Het vraag veld kan niet leeg zijn, probeer een vraag in te voeren",
                    "Het veld vraag kan niet leeg zijn",
                    "Aub voer het vraag veld in"
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
    public List<QuestionDTO> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public void removeQuestion(int questionID) {
        questionDAO.removeQuestion(questionID);
    }

    @Override
    public List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) {
        return questionDAO.getPendingAnswers(campaignId, questionState);
    }

    @Override
    public QuestionDTO getQuestion(int questionid) {
        return questionDAO.getQuestion(questionid);
    }

    @Override
    public void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) {
        questionDAO.setPendingAnswer(givenAnswerDTO);
    }

    @Override
    public AmountOfQuestionTypeCollection countQuestions() {
        return questionDAO.countQuestions();
    }
}
