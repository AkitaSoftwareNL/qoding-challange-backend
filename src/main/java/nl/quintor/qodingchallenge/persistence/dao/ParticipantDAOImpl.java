package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotPersistParticipentException;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotPersistPropertyException;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotRecievePropertyException;
import nl.quintor.qodingchallenge.persistence.exception.ParticipentHasAlreadyParticipatedInCampaignException;
import nl.quintor.qodingchallenge.service.QuestionState;
import nl.quintor.qodingchallenge.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class ParticipantDAOImpl implements ParticipantDAO {

    @Override
    public AnswerCollection getFirstAndLastname(String participantID) {
        AnswerCollection answerCollection = new AnswerCollection();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT FIRSTNAME, INSERTION, LASTNAME FROM CONFERENCE WHERE PARTICIPANTID = ?"
            );
            statement.setString(1, participantID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            answerCollection.setFirstname(resultSet.getString("FIRSTNAME"));
            answerCollection.setInsertion(resultSet.getString("INSERTION"));
            answerCollection.setLastname(resultSet.getString("LASTNAME"));
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Kon niet voor en achternaam ophalen",
                    "Kon niet voor en achternaam ophalen",
                    "Neem contact op support"
            );
        }
        return answerCollection;
    }

    /**
     * <p>Filters the list by the amount of correct answers a person has ASC, and the time in milliseconds DESC.
     * Correct answers is the main filter that will be ran first, secondarily the time will be filtered
     *
     * @param campaignID the campaignID of the campaign that the participant are requested from.
     * @return an ordered list with participants with the highest scoring player first.
     */
    @Override
    public List<ParticipantDTO> getRankedParticipantsPerCampaign(int campaignID) {
        List<ParticipantDTO> participants = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT poc.PARTICIPANTID, poc.CAMPAIGN_ID, poc.TIME_SPEND, c.FIRSTNAME, c.INSERTION, c.LASTNAME, c.EMAIL, c.PHONENUMBER,\n" +
                            "                            (SELECT COUNT(*) FROM given_answer_state AS gas WHERE CAMPAIGN_ID = ? AND gas.PARTICIPANTID = poc.PARTICIPANTID AND gas.STATEID = ?) AS CORRECT\n" +
                            "                            FROM participant_of_campaign AS poc inner join conference as c ON poc.PARTICIPANTID = c.PARTICIPANTID WHERE CAMPAIGN_ID = ? ORDER BY CORRECT DESC, TIME_SPEND");
            statement.setInt(1, campaignID);
            statement.setInt(2, QuestionState.CORRECT.getState());
            statement.setInt(3, campaignID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                participants.add(
                        new ParticipantDTOBuilder().with(participantDTOBuilder -> {
                                    participantDTOBuilder.firstname = resultSet.getString("FIRSTNAME");
                                    participantDTOBuilder.lastname = resultSet.getString("LASTNAME");
                                    participantDTOBuilder.participantID = resultSet.getString("PARTICIPANTID");
                                    participantDTOBuilder.campaignID = resultSet.getInt("CAMPAIGN_ID");
                                    participantDTOBuilder.timeInMillis = resultSet.getLong("TIME_SPEND");
                                    participantDTOBuilder.insertion = resultSet.getString("INSERTION");
                                    participantDTOBuilder.email = resultSet.getString("EMAIL");
                                    participantDTOBuilder.phonenumber = resultSet.getString("PHONENUMBER");
                                    participantDTOBuilder.amountOfRightAnsweredQuestions = resultSet.getInt("CORRECT");
                                }
                        ).build()
                );
            }
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Er iets mis gegaan met het ophalen van de campagene",
                    "Kon niet deelnemer per campagne ophalen",
                    "Neem contact op support"
            );
        }
        return participants;
    }

    @Override
    public String addParticipant(ParticipantDTO participantDTO, int campaignID) {
        final String participantID = UUID.randomUUID().toString();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statementParticipantOfCampaign = connection.prepareStatement("INSERT INTO PARTICIPANT_OF_CAMPAIGN (campaign_id, PARTICIPANTID) VALUES (?, ?)");
            PreparedStatement statementConference = connection.prepareStatement("INSERT INTO CONFERENCE (participantid, firstname, insertion, lastname, email, phonenumber) VALUES (?,?,?,?,?,?)");

            statementParticipantOfCampaign.setInt(1, campaignID);
            statementParticipantOfCampaign.setString(2, participantID);

            statementConference.setString(1, participantID);
            statementConference.setString(2, participantDTO.getFirstname());
            statementConference.setString(3, participantDTO.getInsertion());
            statementConference.setString(4, participantDTO.getLastname());
            statementConference.setString(5, participantDTO.getEmail());
            statementConference.setString(6, participantDTO.getPhonenumber());

            statementConference.executeUpdate();
            statementParticipantOfCampaign.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotPersistParticipentException(
                    "Deelnemer kon niet worden opgeslagen",
                    "Er ging iets mis met het opslaan van de deelnemer",
                    "Neem contact op met support"
            );
        }
        return participantID;
    }

    /**
     * <p>Checks if an participant has already participated in the campaign.
     * In the database an ID is generated for a person.
     * So the duplicate participants are found by searching for a person where all given values are duplicate.
     *
     * @param participantDTO participant object.
     * @param campaignID     campaign identifier.
     * @return true if participant already participated.
     */
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

    /**
     * <p>Adds the time when an user is done with the quiz in the format <strong>YYYY-MM-DD hh:mm:ss</strong>
     *
     * @param participantID participant identifier.
     */
    @Override
    public void addTimeToParticipant(String participantID) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE participant_of_campaign SET TIME_ENDED = ? WHERE PARTICIPANTID = ?"
            );
            statement.setString(1, TimeUtils.getTimeStamp());
            statement.setString(2, participantID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotPersistPropertyException(
                    "Tijd kon niet worden toegevoegd",
                    "Tijd kon niet toegevoegd worden",
                    "Neem contact op met support"
            );
        }
    }
}


