package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class QuestionDAOImpl implements QuestionDAO {



    /*
        Er is gekozen om een stored procedure gebruiken voor het opslaan van meerkeuzevragen
        aangezien de vraag en de antwoordmogelijkheden in verschillende tabellen worden opgeslagen.
        Om te voorkomen dat het ene insert-statement wel wordt uitgevoerd en de andere niet, is
        kozen om een stored procedure te maken. Het enige nadeel is dat een stored procedure geen
        array kan verwerken. Vandaar dat de antwoordmogelijkheden aan elkaar geplakt als String worden
        meegeven. In de procedure worden ze weer gescheiden van elkaar.
     */
    @Override
    public void persistMultipleQuestion(QuestionDTO question) throws SQLException {
        final String delimiter = "&";
        List<String> possibleAnswersString = makeString(question.getPossibleAnswers(), delimiter);
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("CALL SP_MultipleChoiceQuestion(?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, "JAVA");
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getQuestionType());
            statement.setString(4, question.getAttachment());
            statement.setString(5, possibleAnswersString.get(0));
            statement.setString(6, possibleAnswersString.get(1));
            statement.setInt(7, question.getPossibleAnswers().size());
            statement.setString(8, delimiter);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private List<String> makeString(List<PossibleAnswerDTO> possibleAnswers, String delimiter) {
        List<String> possibleAnswersString = new ArrayList<>();
        String possibleAnswerString = delimiter;
        String isCorrectString = delimiter;

        for (PossibleAnswerDTO possibleAnswer : possibleAnswers) {
            possibleAnswerString = possibleAnswerString.concat(possibleAnswer.getPossibleAnswer() + delimiter);
            isCorrectString = isCorrectString.concat(possibleAnswer.getIs_Correct() + delimiter);
        }

        possibleAnswersString.add(possibleAnswerString);
        possibleAnswersString.add(isCorrectString);

        return possibleAnswersString;
    }
}


