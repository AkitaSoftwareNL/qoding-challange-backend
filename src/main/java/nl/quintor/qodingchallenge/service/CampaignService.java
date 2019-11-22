package nl.quintor.qodingchallenge.service;

import nl.quintor.dto.CampaignDTO;
import nl.quintor.qodingchallenge.percistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.percistence.exception.CampaignAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface CampaignService {

    void setCampaignDAO(CampaignDAO campaignDAO);

    List<CampaignDTO> createNewCampaign(String name) throws SQLException, CampaignAlreadyExistsException;

    List<CampaignDTO> showCampaign() throws SQLException;
}
