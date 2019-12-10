package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;

import java.sql.SQLException;

public abstract class QuestionStrategy {

    QuestionDAO questionDAO;
    private String type;

    QuestionStrategy(QuestionDAO questionDAO, String type) {
        this.questionDAO = questionDAO;
        this.type = type;
    }

    public boolean isType(String typeToCheck) {
        return this.type.equals(typeToCheck);
    }

    public void persistQuestion(QuestionDTO question) throws SQLException {
        questionDAO.persistOpenQuestion(question);
    }

    public void validateAnswer(QuestionDTO question) throws SQLException {

    }
}
