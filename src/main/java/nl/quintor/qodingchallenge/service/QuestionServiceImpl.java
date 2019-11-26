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
    @Override
    public void setQuestionPersistence(QuestionPersistence questionPersistence) {
        this.questionPersistence = questionPersistence;
    }

    @Override
    public List<QuestionDTO> getQuestions(String category, int amountOfQuestions) throws SQLException {
        List<QuestionDTO> questions = questionPersistence.getQuestions(category, amountOfQuestions);

        for (QuestionDTO questionDTO : questions) {
            questionDTO
                    .setPossibleAnswer(questionPersistence
                            .getPossibleAnswers(questionDTO
                                    .getQuestionID())
                    );
        }

        return questions;
    }

    @Override
    public void setAnswer(QuestionCollection questionCollection) throws SQLException {
        final int CORRECT = 2;
        final int INCORRECT = 3;
        final String TYPE = "multiple";

        for (QuestionDTO question : questionCollection.getQuestions()) {
            if (question.getQuestionType().equals(TYPE)) {
                String correctAnswer = questionPersistence.getCorrectAnswer(question.getQuestionID());
                if (checkAnswer(correctAnswer, question.getGivenAnswer())) {
                    question.setStateID(CORRECT);
                } else {
                    question.setStateID(INCORRECT);
                }
            }
            questionPersistence.setAnswer(question, questionCollection.getCampaignName(), questionCollection.getParticipantID());
        }
    }

    private boolean checkAnswer(String correctAnswer, String givenAnswer) {
        return correctAnswer.equals(givenAnswer);
    }
}
