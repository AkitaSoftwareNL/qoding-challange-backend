package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.persistence.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.QuestionDAOimpl;

public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO = new QuestionDAOimpl();

    @Override
    public void validateAnswer(int participantName, String givenAnswer, String questionType) {
        String correctAnswer = questionDAO.getCorrectAnswer();

        
    }
}
