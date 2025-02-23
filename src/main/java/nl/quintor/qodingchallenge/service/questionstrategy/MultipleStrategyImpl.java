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
    public void persistQuestion(QuestionDTO question) {
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

        for (String givenAnswer : givenAnswers) {
            if (question.getStateID() != QuestionState.INCORRECT.getState()) {
                checkAnswers(givenAnswer, correctAnswers, question);
            }
        }

        if (question.getStateID() == QuestionState.PENDING.getState()) {
            question.setStateID(QuestionState.CORRECT.getState());
        }
    }

    private void checkAnswers(String givenAnswer, ArrayList<PossibleAnswerDTO> correctAnswers, QuestionDTO question) {
        for (PossibleAnswerDTO correctAnswer : correctAnswers) {
            if (correctAnswer.getPossibleAnswer().equals(givenAnswer)) {
                return;
            }
        }
        question.setStateID(QuestionState.INCORRECT.getState());
    }
}
