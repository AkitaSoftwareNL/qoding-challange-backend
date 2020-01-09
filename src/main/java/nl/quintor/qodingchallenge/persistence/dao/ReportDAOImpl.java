package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class ReportDAOImpl implements ReportDAO {

    @Override
    public List<AnswerDTO> getAnswersPerParticipant(int campaignID, String participantID) throws SQLException {
        List<AnswerDTO> answers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT ga.AUTOID, ga.GIVEN_ANSWER, q.QUESTION, gas.STATEID, q.QUESTION_TYPE from given_answer as ga left OUTER JOIN given_answer_state as gas\n" +
                            "ON ga.QUESTIONID = gas.QUESTIONID AND ga.CAMPAIGN_ID = gas.CAMPAIGN_ID AND ga.PARTICIPANTID = gas.PARTICIPANTID\n" +
                            "left outer join question as q ON ga.QUESTIONID = q.QUESTIONID\n" +
                            "WHERE ga.CAMPAIGN_ID = ? AND ga.PARTICIPANTID = ?");
            statement.setInt(1, campaignID);
            statement.setString(2, participantID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                answers.add(
                        new AnswerDTO(
                                resultSet.getString("GIVEN_ANSWER"),
                                resultSet.getString("QUESTION"),
                                resultSet.getInt("STATEID"),
                                resultSet.getString("QUESTION_TYPE")
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return answers;
    }
}

