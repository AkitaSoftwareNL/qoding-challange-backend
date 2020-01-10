package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.QuestionType;

import java.sql.SQLException;

public abstract class QuestionStrategy {

    QuestionDAO questionDAO;
    private QuestionType type;

    QuestionStrategy(QuestionDAO questionDAO, QuestionType type) {
        this.questionDAO = questionDAO;
        this.type = type;
    }

    public boolean isType(String typeToCheck) {
        return type.getState() == QuestionType.getEnumAsInt(typeToCheck);
    }

    public void persistQuestion(QuestionDTO question) throws SQLException {
        questionDAO.persistOpenQuestion(question);
    }

    public void validateAnswer(QuestionDTO question) throws SQLException {

    }
}
