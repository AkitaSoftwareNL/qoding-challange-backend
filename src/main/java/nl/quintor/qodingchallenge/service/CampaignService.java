package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.exception.CampaignAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface CampaignService {

    void setCampaignDAO(CampaignDAO campaignDAO);

    List<CampaignDTO> createNewCampaign(String name) throws SQLException, CampaignAlreadyExistsException;

    List<CampaignDTO> showCampaign() throws SQLException;
}
