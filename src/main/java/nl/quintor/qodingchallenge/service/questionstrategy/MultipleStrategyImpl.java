package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.service.QuestionType;

import java.sql.SQLException;
import java.util.ArrayList;

public class MultipleStrategyImpl extends QuestionStrategy {
    public MultipleStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, QuestionType.MULTIPLE);
    }

    @Override
    public void persistQuestion(QuestionDTO question) throws SQLException {
        questionDAO.persistMultipleQuestion(question);
    }

    @Override
    public void validateAnswer(QuestionDTO question) throws SQLException {
        ArrayList<PossibleAnswerDTO> correctAnswers = questionDAO.getCorrectAnswers(question.getQuestionID());
        String[] givenAnswers = question.getGivenAnswers();

        if (correctAnswers.size() != givenAnswers.length) {
            question.setStateID(QuestionState.INCORRECT.getState());
            return;
        }

        loopBunny:
        for (String givenAnswer : givenAnswers) {
            for (PossibleAnswerDTO correctAnswer : correctAnswers) {
                if (correctAnswer.getPossibleAnswer().equals(givenAnswer)) {
                    continue loopBunny;
                }
            }
            question.setStateID(QuestionState.INCORRECT.getState());
            return;
        }
        question.setStateID(QuestionState.CORRECT.getState());
    }
}
