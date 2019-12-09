package nl.quintor.qodingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

@EnableGlobalAuthentication
@SpringBootApplication
public class QodingchallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(QodingchallengeApplication.class, args);
    }
}
