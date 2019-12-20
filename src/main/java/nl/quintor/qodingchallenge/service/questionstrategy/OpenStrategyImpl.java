package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionType;

public class OpenStrategyImpl extends QuestionStrategy {
    public OpenStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, QuestionType.OPEN);
    }
}

