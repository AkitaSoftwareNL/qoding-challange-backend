package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class ParticipantDAOImpl implements ParticipantDAO {

    @Override
    public AnswerCollection getFirstAndLastname(int participantID) throws SQLException {
        AnswerCollection answerCollection = new AnswerCollection();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT FIRSTNAME, INSERTION, LASTNAME FROM CONFERENCE WHERE PARTICIPANTID = ?"
            );
            statement.setInt(1, participantID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            answerCollection.setFirstname(resultSet.getString("FIRSTNAME"));
            answerCollection.setInsertion(resultSet.getString("INSERTION"));
            answerCollection.setLastname(resultSet.getString("LASTNAME"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return answerCollection;
    }

    @Override
    public void addParticipantToCampaign(int campaignID, int participantID) throws SQLException{
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO participant_of_campaign (CAMPAIGN_ID, PARTICIPANTID) VALUES (?,?)"
            );
            statement.setInt(1, campaignID);
            statement.setInt(2, participantID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}

