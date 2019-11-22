package nl.quintor.qodingchallenge;

import nl.quintor.qodingchallenge.percistence.dao.QuestionDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class QodingchallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(QodingchallengeApplication.class, args);
    }
}
