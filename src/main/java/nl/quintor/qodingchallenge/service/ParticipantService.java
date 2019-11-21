package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.util.List;

public interface ParticipantService {
    void validateAnswer(String participantName, String givenAnswer, String questionType);
}
