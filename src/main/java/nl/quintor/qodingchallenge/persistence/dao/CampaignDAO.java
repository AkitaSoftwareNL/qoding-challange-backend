package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.CampaignDTO;

import java.sql.SQLException;
import java.util.List;

public interface CampaignDAO {
    boolean campaignExists(String name) throws SQLException;

    void persistCampaign(CampaignDTO campaignDTO) throws SQLException;

    List<CampaignDTO> getAllCampaigns() throws SQLException;

    int getAmountOfQuestions(String campaignName) throws SQLException;

}
