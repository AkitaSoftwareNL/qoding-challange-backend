package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.persistence.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.QuestionDAOimpl;

public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO = new QuestionDAOimpl();

    @Override
    public void validateAnswer(GivenAnswerDTO givenAnswer) {
        // Gets the correct answer of the asked question
        String correctAnswer = questionDAO.getCorrectAnswer(givenAnswer.getQuestionID());

        if(checkAnswer(correctAnswer, givenAnswer.getGivenAnswer())) {
            givenAnswer.setStateID(2); // correct
        } else {
            givenAnswer.setStateID(3); // incorrect
        }

        questionDAO.persistAnswer(givenAnswer);
    }

    private boolean checkAnswer(String correctAnswer, String givenAnswer) {
        return correctAnswer.equals(givenAnswer);
    }
}
