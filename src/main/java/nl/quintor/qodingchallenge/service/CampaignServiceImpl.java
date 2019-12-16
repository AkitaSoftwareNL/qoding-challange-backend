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
import java.util.Random;

import static java.lang.String.format;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
    private CampaignDAO campaignDAO;

    @Override
    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    public void createNewCampaign(CampaignDTO campaignDTO) throws SQLException {
        if (campaignDAO.campaignExists(campaignDTO.getId())) {
            logger.warn(format("Campaign %s already exists, try an other name.", campaignDTO.getName()));
            throw new CampaignAlreadyExistsException(
                    "the campaign already exists",
                    format("Campaign name = %s", campaignDTO.getName()),
                    format("Try another name like %s%04d", campaignDTO.getName(), new Random().nextInt(9999))
            );
        }
        campaignDAO.persistCampaign(campaignDTO);
    }

    @Override
    public List<CampaignDTO> showCampaign() throws SQLException {
        return campaignDAO.getAllCampaigns();
    }
}
