package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class CampaignDAOImpl implements CampaignDAO {

    @Override
    public boolean campaignExists(String name) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT 1 FROM campaign WHERE CAMPAIGN_NAME = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }

    @Override
    public void persistCampaign(CampaignDTO campaignDTO) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO campaign(CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, AMOUNT_OF_QUESTIONS, TIMELIMIT, STATE)" +
                            "VALUES (?, 'JAVA', 'conferentie', 'admin', ?, null, 1)");
            statement.setString(1, campaignDTO.getName());
            statement.setInt(2, campaignDTO.getAmountOfQuestions());
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() throws SQLException {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT CAMPAIGN_ID, CAMPAIGN_NAME, CATEGORY_NAME, USERNAME, AMOUNT_OF_QUESTIONS, TIMESTAMP_CREATED, STATE FROM campaign");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                campaignDTOList.add(
                        new CampaignDTO(
                                resultSet.getInt("CAMPAIGN_ID"),
                                resultSet.getString("CAMPAIGN_NAME"),
                                resultSet.getString("CATEGORY_NAME"),
                                resultSet.getString("USERNAME"),
                                resultSet.getInt("AMOUNT_OF_QUESTIONS"),
                                resultSet.getString("TIMESTAMP_CREATED"),
                                resultSet.getInt("STATE"),
                                null
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return campaignDTOList;
    }

    @Override
    public int getAmountOfQuestions(String campaignName) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT AMOUNT_OF_QUESTIONS FROM campaign WHERE CAMPAIGN_NAME = ?");
            statement.setString(1, campaignName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AMOUNT_OF_QUESTIONS");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getCampaignName(int campaignID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT CAMPAIGN_NAME FROM campaign WHERE CAMPAIGN_ID = ?");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("CAMPAIGN_NAME");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getCampaignID(String campaignName) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT CAMPAIGN_ID FROM campaign WHERE CAMPAIGN_NAME = ?");
            statement.setString(1, campaignName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("CAMPAIGN_ID");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
