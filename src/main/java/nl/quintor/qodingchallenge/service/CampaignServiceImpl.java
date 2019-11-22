package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.percistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.percistence.exception.CampaignAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignDAO campaignDAO;

    @Override
    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    public List<CampaignDTO> createNewCampaign(String name) throws SQLException, CampaignAlreadyExistsException {
        campaignDAO.campaignExists(name);
        campaignDAO.persistCampaign(name);
        return campaignDAO.getAllCampaigns();
    }

    @Override
    public List<CampaignDTO> showCampaign() throws SQLException {
        return campaignDAO.getAllCampaigns();
    }
}
