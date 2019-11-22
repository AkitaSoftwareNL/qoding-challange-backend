package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public interface QuestionService {
    List<QuestionDTO> getQuestions(String category, int amountOfQuestions) throws SQLException;

    void setAnswer(GivenAnswerlistDTO givenAnswerlistDTO) throws SQLException;
}
