package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AnswerDTO;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.RankedParticipantCollection;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReportService {

    private ReportDAO reportDAO;

    private CampaignDAO campaignDAO;

    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    public List<CampaignDTO> getAllCampaings() throws SQLException {
        return campaignDAO.getAllCampaigns();
    }

    public RankedParticipantCollection getRankedParticipantsPerCampaign(int campaignID) throws SQLException {
        return new RankedParticipantCollection(
                campaignDAO.getCampaignName(campaignID),
                reportDAO.getRankedParticipantsPerCampaign(campaignID)
        );
    }

    public List<AnswerDTO> getAnswersPerParticipant(int campaignID, int participantID) throws SQLException {
        return reportDAO.getAnswersPerParticipant(campaignID, participantID);
    }
}
