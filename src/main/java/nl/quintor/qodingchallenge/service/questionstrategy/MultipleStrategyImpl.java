package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.service.QuestionType;

import java.sql.SQLException;

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
        String correctAnswer = questionDAO.getCorrectAnswer(question.getQuestionID());
        if (correctAnswer.equals(question.getGivenAnswer())) {
            question.setStateID(QuestionState.CORRECT.getState());
        } else {
            question.setStateID(QuestionState.INCORRECT.getState());
        }
    }
}
