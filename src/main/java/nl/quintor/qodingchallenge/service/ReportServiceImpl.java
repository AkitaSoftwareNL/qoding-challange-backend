package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.dto.RankedParticipantCollection;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.persistence.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportDAO reportDAO;
    private ParticipantDAO participantDAO;
    private CampaignDAO campaignDAO;

    @Override
    @Autowired
    public void setReportDAO(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    @Autowired
    public void setParticipantDAO(ParticipantDAO participantDAO) {
        this.participantDAO = participantDAO;
    }

    @Override
    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() throws SQLException {
        return campaignDAO.getAllCampaigns(true);
    }

    @Override
    public RankedParticipantCollection getRankedParticipantsPerCampaign(int campaignID) throws SQLException {
        return new RankedParticipantCollection(
                campaignDAO.getCampaignName(campaignID),
                participantDAO.getRankedParticipantsPerCampaign(campaignID)
        );
    }

    @Override
    public AnswerCollection getAnswersPerParticipant(int campaignID, String participantID) throws SQLException {
        AnswerCollection answerCollection = participantDAO.getFirstAndLastname(participantID);
        answerCollection.setCampaignName(campaignDAO.getCampaignName(campaignID));
        answerCollection.setCampaignID(campaignID);
        answerCollection
                .setAnswers(reportDAO.getAnswersPerParticipant(campaignID, participantID))
                .filter();

        return answerCollection;
    }
}
