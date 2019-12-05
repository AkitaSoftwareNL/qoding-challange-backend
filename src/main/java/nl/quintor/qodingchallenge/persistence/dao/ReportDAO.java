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
public class ReportDAO {

    public List<ParticipantDTO> getRankedParticipantsPerCampaign(int campaignID) throws SQLException {
        List<ParticipantDTO> participants = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT poc.PARTICIPANTID, poc.CAMPAIGN_ID" +
                            ",poc.TIME_SPEND, c.FIRSTNAME, c.INSERTION, c.LASTNAME, c.EMAIL, c.PHONENUMBER, " +
                            "(SELECT COUNT(*) FROM given_answer  WHERE poc.CAMPAIGN_ID = CAMPAIGN_ID AND poc.PARTICIPANTID = PARTICIPANTID AND STATEID = 1) AS CORRECT " +
                            "FROM qodingchallenge.participant_of_campaign AS poc inner join conference as c ON poc.PARTICIPANTID = c.PARTICIPANTID WHERE CAMPAIGN_ID = ? ORDER BY CORRECT DESC, TIME_SPEND");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                participants.add(
                        new ParticipantDTO(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getTime(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getInt(8),
                                resultSet.getInt(9)
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return participants;
    }

    public List<AnswerDTO> getAnswersPerParticipant(int campaignID, int participantID) throws SQLException {
        List<AnswerDTO> answers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.FIRSTNAME, c.LASTNAME, ga.GIVEN_ANSWER, q.QUESTION, q.STATE, q.QUESTION_TYPE FROM given_answer as ga INNER JOIN question q " +
                            "ON ga.QUESTIONID = q.QUESTIONID INNER JOIN conference as c " +
                            "ON ga.PARTICIPANTID = c.PARTICIPANTID WHERE ga.CAMPAIGN_ID = ? AND ga.PARTICIPANTID = ?");
            statement.setInt(1, campaignID);
            statement.setInt(2, participantID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                answers.add(
                        new AnswerDTO(
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getString(6)
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return answers;
    }
}

