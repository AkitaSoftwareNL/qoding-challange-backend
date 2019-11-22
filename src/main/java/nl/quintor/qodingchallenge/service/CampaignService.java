package nl.quintor.qodingchallenge.service;

import nl.quintor.dto.CampaignDTO;
import nl.quintor.qodingchallenge.percistence.dao.CampaignDAO;

import java.sql.SQLException;
import java.util.List;

public class CampaignService {

    private CampaignDAO campaignDAO = new CampaignDAO();

    public List<CampaignDTO> createNewCampaign(String name) throws SQLException {
        campaignDAO.persistCampaign(name);
        return campaignDAO.getAllCampaigns();
    }

    public List<CampaignDTO> showCampaign() throws SQLException {
        return campaignDAO.getAllCampaigns();
    }
}
