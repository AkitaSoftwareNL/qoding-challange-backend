package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionPersistence questionPersistence;

    @Autowired
    public void setQuestionPersistence(QuestionPersistence questionPersistence) {
        this.questionPersistence = questionPersistence;
    }

    @Override
    public List<QuestionDTO> getQuestions(String category, int amountOfQuestions) throws SQLException {
        List<QuestionDTO> questions = questionPersistence.getQuestions(category, amountOfQuestions);

        for (QuestionDTO questionDTO : questions) {
            questionDTO
                    .setPossibleAnswers(questionPersistence.getPossibleAnswers(questionDTO.getQuestionID())
                    );
        }

        return questions;
    }

    @Override
    public void setAnswer(GivenAnswerlistDTO givenAnswerlistDTO) throws SQLException {
        for (GivenAnswerDTO answer : givenAnswerlistDTO.getGivenAnswerDTO()) {
            questionPersistence.setAnswer(answer);
        }
            // Gets the correct answer of the asked question
//            String correctAnswer = questionDAO.getCorrectAnswer(givenAnswer.getQuestionID());
//
//            if(checkAnswer(correctAnswer, givenAnswer.getGivenAnswer())) {
//                givenAnswer.setStateID(2); // correct
//            } else {
//                givenAnswer.setStateID(3); // incorrect
//            }
//
//            questionDAO.persistAnswer(givenAnswer);

    }

    private boolean checkAnswer(String correctAnswer, String givenAnswer) {
        return correctAnswer.equals(givenAnswer);
    }

}
