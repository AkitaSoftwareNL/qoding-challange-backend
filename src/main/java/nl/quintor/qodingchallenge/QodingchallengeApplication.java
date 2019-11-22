package nl.quintor.qodingchallenge;

import nl.quintor.qodingchallenge.percistence.dao.QuestionDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class QodingchallengeApplication {
    public static void main(String[] args) throws SQLException {
        var question = new QuestionDAO().getQuestions("java", 3);
        question.forEach(questionDTO -> System.out.println(questionDTO.getQuestionName()));
        SpringApplication.run(QodingchallengeApplication.class, args);
    }
}
