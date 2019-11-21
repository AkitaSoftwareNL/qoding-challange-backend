package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.percistence.dao.IQuestionPercistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

public class QuestionServiceImpl implements IQuestionService {

    private IQuestionPercistence questionPercistence;

    @Bean
    public void setQuestionPercistence(IQuestionPercistence questionPercistence) {
        this.questionPercistence = questionPercistence;
    }
    
    @Override
    public List<QuestionDTO> getQuestions(String category, int amountOfQuestions) throws SQLException {
        return questionPercistence.getQuestions(category, amountOfQuestions);
    }
}
