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
                        new ParticipantDTO.Builder(resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"))
                                .id(resultSet.getInt("PARTICIPANTID"))
                                .participatedCampaignID(resultSet.getInt("CAMPAIGN_ID"))
                                .timeOf(resultSet.getLong("TIME_SPEND"))
                                .insertion(resultSet.getString("INSERTION"))
                                .email(resultSet.getString("EMAIL"))
                                .phonenumber(resultSet.getString("PHONENUMBER"))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return participants;
    }

    @Override
    public void addParticipant(ParticipantDTO participantDTO, int campaignID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statementParticipant = connection.prepareStatement("INSERT INTO PARTICIPANT VALUES (?)");
            PreparedStatement statementParticipantOfCampaign = connection.prepareStatement("INSERT INTO PARTICIPANT_OF_CAMPAIGN (participantid, campaign_id) VALUES (?, ?)");
            PreparedStatement statementConference = connection.prepareStatement("INSERT INTO CONFERENCE (participantid, firstname, insertion, lastname, email, phonenumber) VALUES (?,?,?,?,?,?)");

            statementParticipant.setInt(1, participantDTO.getParticipantID());

            statementParticipantOfCampaign.setInt(1, participantDTO.getParticipantID());
            statementParticipantOfCampaign.setInt(2, campaignID);

            statementConference.setInt(1, participantDTO.getParticipantID());
            statementConference.setString(2, participantDTO.getFirstname());
            statementConference.setString(3, participantDTO.getInsertion());
            statementConference.setString(4, participantDTO.getLastname());
            statementConference.setString(5, participantDTO.getEmail());
            statementConference.setString(6, participantDTO.getPhonenumber());

            statementParticipant.executeUpdate();
            statementParticipantOfCampaign.executeUpdate();
            statementConference.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    @Override
    public boolean participantHasAlreadyParticipatedInCampaign(ParticipantDTO participantDTO, int campaignID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT poc.participantID\n" +
                            "FROM PARTICIPANT_OF_CAMPAIGN as poc INNER JOIN CONFERENCE as C\n" +
                            "ON poc.participantID = C.participantID\n" +
                            "WHERE poc.campaign_ID = ? AND\n" +
                            "c.firstname = ? AND\n" +
                            "(c.insertion IS NULL OR c.insertion = ?) AND\n" +
                            "c.lastname = ? AND\n" +
                            "(c.email IS NULL OR c.email = ?) AND \n" +
                            "(c.phonenumber IS NULL OR c.phonenumber = ?)"
            );
            statement.setInt(1, campaignID);
            statement.setString(2, participantDTO.getFirstname());
            statement.setString(3, participantDTO.getInsertion());
            statement.setString(4, participantDTO.getLastname());
            statement.setString(5, participantDTO.getEmail());
            statement.setString(6, participantDTO.getPhonenumber());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }
}

