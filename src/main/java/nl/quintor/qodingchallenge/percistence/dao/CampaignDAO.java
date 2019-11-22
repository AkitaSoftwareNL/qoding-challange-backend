package nl.quintor.qodingchallenge.percistence.dao;

import nl.quintor.dto.CampaignDTO;
import nl.quintor.dto.ParticipantDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.percistence.connection.ConnectionPoolFactory.getConnection;

public class CampaignDAO {
    ParticipantDTO participantDTO = new ParticipantDTO(1, "Nick", "", "Braks", "nick.braks@live.nl", 646688038);

    public Boolean persistCampaign(String name) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO campaign(NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, AMOUNT_OF_QUESTIONS, TIMELIMIT, STATE)" +
                            "VALUES (?, 'conferentie', 'JAVA', 'admin', 2, null, 1)");
            statement.setString(1, name);
            if (statement.execute()) {
                return true;
            }
            System.out.println("FALSEEEE");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }

    public List<CampaignDTO> getAllCampaigns() throws SQLException {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT NAME, CATEGORY_NAME, USERNAME, AMOUNT_OF_QUESTIONS FROM campaign");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CampaignDTO campaignDTO = new CampaignDTO();
                campaignDTO.setName(resultSet.getString(1));
                campaignDTO.setCategory(resultSet.getString(2));
                campaignDTO.setStartedby(resultSet.getString(3));
                campaignDTO.setAmountOfQuestions(resultSet.getInt(4));
                campaignDTO.setParticipants(null);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
