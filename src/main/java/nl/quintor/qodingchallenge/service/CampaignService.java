package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;

import java.sql.SQLException;
import java.util.List;

public interface CampaignService {

    void setCampaignDAO(CampaignDAO campaignDAO);

    void createNewCampaign(CampaignDTO campaignDTO) throws SQLException;

    List<CampaignDTO> showCampaign() throws SQLException;

    void deleteCampaign(int campaignID) throws SQLException;
}
