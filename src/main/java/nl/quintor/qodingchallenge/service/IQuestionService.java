package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTOCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public interface IQuestionService {
    List<QuestionDTO> getQuestions(String category, int amountOfQuestions) throws SQLException;

    void setAnswer(GivenAnswerDTOCollection givenAnswerDTOCollection) throws SQLException;
}
