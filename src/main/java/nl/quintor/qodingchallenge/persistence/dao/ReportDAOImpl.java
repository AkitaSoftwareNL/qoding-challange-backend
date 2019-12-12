package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerDTO;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
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
    public List<ParticipantDTO> getRankedParticipantsPerCampaign(int campaignID) throws SQLException {
        List<ParticipantDTO> participants = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT poc.PARTICIPANTID, poc.CAMPAIGN_ID" +
                            ",poc.TIME_SPEND, c.FIRSTNAME, c.INSERTION, c.LASTNAME, c.EMAIL, c.PHONENUMBER, " +
                            "(SELECT COUNT(*) FROM given_answer  WHERE poc.CAMPAIGN_ID = CAMPAIGN_ID AND poc.PARTICIPANTID = PARTICIPANTID AND STATEID = 1) AS CORRECT " +
                            "FROM participant_of_campaign AS poc inner join conference as c ON poc.PARTICIPANTID = c.PARTICIPANTID WHERE CAMPAIGN_ID = ? ORDER BY CORRECT DESC, TIME_SPEND");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                participants.add(
                        new ParticipantDTO(
                                resultSet.getInt("PARTICIPANTID"),
                                resultSet.getInt("CAMPAIGN_ID"),
                                resultSet.getLong("TIME_SPEND"),
                                resultSet.getString("FIRSTNAME"),
                                resultSet.getString("INSERTION"),
                                resultSet.getString("LASTNAME"),
                                resultSet.getString("EMAIL"),
                                resultSet.getString("PHONENUMBER"),
                                resultSet.getInt("CORRECT")
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return participants;
    }

    @Override
    public List<AnswerDTO> getAnswersPerParticipant(int campaignID, int participantID) throws SQLException {
        List<AnswerDTO> answers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT ga.GIVEN_ANSWER, q.QUESTION, q.STATE, q.QUESTION_TYPE FROM given_answer as ga INNER JOIN question q " +
                            "ON ga.QUESTIONID = q.QUESTIONID INNER JOIN conference as c " +
                            "ON ga.PARTICIPANTID = c.PARTICIPANTID WHERE ga.CAMPAIGN_ID = ? AND ga.PARTICIPANTID = ?");
            statement.setInt(1, campaignID);
            statement.setInt(2, participantID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                answers.add(
                        new AnswerDTO(
                                resultSet.getString("GIVEN_ANSWER"),
                                resultSet.getString("QUESTION"),
                                resultSet.getInt("STATE"),
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

