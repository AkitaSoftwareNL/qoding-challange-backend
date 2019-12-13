package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
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
public class ParticipantDAOImpl implements ParticipantDAO {

    @Override
    public AnswerCollection getFirstAndLastname(int participantID) throws SQLException {
        AnswerCollection answerCollection = new AnswerCollection();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT firstname, INSERTION, lastname FROM conference WHERE participantID = ?"
            );
            statement.setInt(1, participantID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            answerCollection.setFirstname(resultSet.getString(1));
            answerCollection.setInsertion(resultSet.getString(2));
            answerCollection.setLastname(resultSet.getString(3));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return answerCollection;
    }

    @Override
    public void addParticipantToCampaign(int campaignID, int participantID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO participant_of_campaign(CAMPAIGN_ID, PARTICIPANTID) VALUES (?,?)"
            );
            statement.setInt(1, campaignID);
            statement.setInt(2, participantID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<ParticipantDTO> getParticipantsPerCampaign(int campaignID) throws SQLException {
        List<ParticipantDTO> participants = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM participant_of_campaign as poc inner join conference as c \n" +
                            "on poc.PARTICIPANTID = c.PARTICIPANTID\n" +
                            "WHERE poc.CAMPAIGN_ID = ?;"
            );
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
                                resultSet.getString("PHONENUMBER")
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return participants;
    }
}

