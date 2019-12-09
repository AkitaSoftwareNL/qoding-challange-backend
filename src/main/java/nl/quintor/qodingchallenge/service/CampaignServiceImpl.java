package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignDAO campaignDAO;
    private final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

    @Override
    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    public void createNewCampaign(CampaignDTO campaignDTO) throws SQLException {
        if (campaignDAO.campaignExists(campaignDTO.getName())) {
            logger.warn("Campaign already exists, try an other name.");
            throw new CampaignAlreadyExistsException("The campaign " + campaignDTO.getName() + " already exists.");
        }
        campaignDAO.persistCampaign(campaignDTO);
    }

    @Override
    public List<CampaignDTO> showCampaign() throws SQLException {
        return campaignDAO.getAllCampaigns();
    }
}
