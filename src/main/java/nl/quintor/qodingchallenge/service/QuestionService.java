package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;

public interface QuestionService {
    void validateAnswer(GivenAnswerDTO givenAnswer);
}
