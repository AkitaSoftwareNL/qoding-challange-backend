package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;

import java.sql.SQLException;

public class CodingStrategyImpl extends QuestionStrategy {

    public CodingStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, "coding");
    }

    @Override
    public void validateAnswer(QuestionDTO question) throws SQLException {
        final int CORRECT = 2;
        final int INCORRECT = 3;

        if (false) {
            question.setStateID(CORRECT);
        } else {
            question.setStateID(INCORRECT);
        }
    }
}
