package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.CampaignDTO;

import java.sql.SQLException;
import java.util.List;

public interface CampaignDAO {
    boolean campaignExists(int id);

    void persistCampaign(CampaignDTO campaignDTO);

    List<CampaignDTO> getAllCampaigns();

    List<CampaignDTO> getAllCampaigns(boolean all);

    AmountOfQuestionTypeCollection getAmountOfQuestions(int campaignID);

    String getCampaignName(int campaignID);

    int getCampaignID(String campaignName);

    void deleteCampaign(int campaignID);
}
