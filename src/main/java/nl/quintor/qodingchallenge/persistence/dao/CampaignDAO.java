package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.CampaignDTO;

import java.sql.SQLException;
import java.util.List;

public interface CampaignDAO {
    boolean campaignExists(int id) throws SQLException;

    void persistCampaign(CampaignDTO campaignDTO) throws SQLException;

    List<CampaignDTO> getAllCampaigns() throws SQLException;

    List<CampaignDTO> getAllCampaigns(boolean all) throws SQLException;

    AmountOfQuestionTypeCollection getAmountOfQuestions(int campaignID) throws SQLException;

    String getCampaignName(int campaignID) throws SQLException;

    int getCampaignID(String campaignName) throws SQLException;

    void deleteCampaign(int campaignID) throws SQLException;
}
