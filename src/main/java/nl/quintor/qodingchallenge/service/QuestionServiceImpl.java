package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.percistence.dao.QuestionPercistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionPercistence questionPercistence;

    @Autowired
    public void setQuestionPercistence(QuestionPercistence questionPercistence) {
        this.questionPercistence = questionPercistence;
    }
    
    @Override
    public List<QuestionDTO> getQuestions(String category, int amountOfQuestions) throws SQLException {
        return questionPercistence.getQuestions(category, amountOfQuestions);
    }

    @Override
    public void setAnswer(GivenAnswerlistDTO givenAnswerlistDTO) throws SQLException {
        for (GivenAnswerDTO answer : givenAnswerlistDTO.getGivenAnswerDTO()) {
            questionPercistence.setAnswer(answer);
        }
    }
}
