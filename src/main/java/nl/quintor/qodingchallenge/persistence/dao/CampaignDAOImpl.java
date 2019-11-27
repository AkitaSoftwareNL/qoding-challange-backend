package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.exception.CampaignAlreadyExistsException;
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
    public void campaignExists(String name) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT 1 FROM campaign WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                throw new CampaignAlreadyExistsException("The campaign " + name + " already exists.");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void persistCampaign(String name) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO campaign(NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, AMOUNT_OF_QUESTIONS, TIMELIMIT, STATE)" +
                            "VALUES (?, 'JAVA', 'conferentie', 'admin', 2, null, 1)");
            statement.setString(1, name);
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
                    "SELECT NAME, CATEGORY_NAME, USERNAME, AMOUNT_OF_QUESTIONS FROM campaign");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                campaignDTOList.add(
                        new CampaignDTO(
                                resultSet.getString(1),
                                resultSet.getInt(4),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                null //TODO SET REFERENCE
                        )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e);
    }
        return campaignDTOList;
    }
}
