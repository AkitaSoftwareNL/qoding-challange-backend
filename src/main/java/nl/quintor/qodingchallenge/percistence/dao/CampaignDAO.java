package nl.quintor.qodingchallenge.percistence.dao;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.percistence.exception.CampaignAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface CampaignDAO {
    void campaignExists(String name) throws SQLException, CampaignAlreadyExistsException;

    void persistCampaign(String name) throws SQLException;

    List<CampaignDTO> getAllCampaigns() throws SQLException;
}
