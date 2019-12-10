package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;

import java.sql.SQLException;

public class MultipleStrategyImpl extends QuestionStrategy {
    public MultipleStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, "multiple");
    }

    @Override
    public void persistQuestion(QuestionDTO question) throws SQLException {
        questionDAO.persistMultipleQuestion(question);
    }

    @Override
    public void validateAnswer(QuestionDTO question) throws SQLException {
        final int CORRECT = 2;
        final int INCORRECT = 3;

        String correctAnswer = questionDAO.getCorrectAnswer(question.getQuestionID());
        if (correctAnswer.equals(question.getGivenAnswer())) {
            question.setStateID(CORRECT);
        } else {
            question.setStateID(INCORRECT);
        }
    }
}
