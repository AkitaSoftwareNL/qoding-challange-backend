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
}
