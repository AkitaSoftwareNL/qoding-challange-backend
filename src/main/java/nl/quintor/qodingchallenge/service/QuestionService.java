package nl.quintor.qodingchallenge.service;

public interface QuestionService {
    void validateAnswer(int participantName, String givenAnswer, String questionType);
}
