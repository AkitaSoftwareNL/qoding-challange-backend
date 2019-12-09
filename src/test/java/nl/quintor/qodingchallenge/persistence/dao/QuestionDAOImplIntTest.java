package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionDAOImplIntTest {

    private QuestionDAOImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new QuestionDAOImpl();
        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        Op dit moment wordt er met het testen een SQLException gegooit. Dit omdat binnen de gebruikte H2Database
        geen stored procedure/functie is met de naam die aangeroepen wordt in het SQL-statement.
        Wanneer de stored procedure in het DDL, geven alle tests een SQLSyntax exception aangezien de H2Database
        geen stored procedures ondersteunt.
     */
    @Test
    void persistMultipleQuestionThrowsSQLException() {
        // Mock

        // Test

        // Verify
        assertThrows(SQLException.class, () -> sut.persistMultipleQuestion(getMultipleQuestion()));
    }

    /*
        Onze intentie was om de vraag op te slaan. En vervolgens te controleren of het aantal vragen
        in de database met één omhoog is gegaan.
     */
    @Test
    void persistMultipleQuestionPersistsMultipleQuestion() throws SQLException {
        // Mock

        // Test
        sut.persistMultipleQuestion(getMultipleQuestion());
        // Verify
        assertEquals(4, get);
    }

    private QuestionDTO getMultipleQuestion() {
        QuestionDTO question = new QuestionDTO(10, "dit is een test vraag", "open", null);
        ArrayList<PossibleAnswerDTO> possibleAnswers = new ArrayList<>() {
            {
                add(new PossibleAnswerDTO("yes", 1));
                add(new PossibleAnswerDTO("no", 0));
            }
        };
        question.setPossibleAnswers(possibleAnswers);
        return question;
    }
}
