package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;

public class OpenStrategyImpl extends QuestionStrategy {
    public OpenStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, "open");
    }
}

