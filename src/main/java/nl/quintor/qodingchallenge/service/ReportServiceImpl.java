package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.*;
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
    public List<CampaignDTO> getAllCampaings() throws SQLException {
        return campaignDAO.getAllCampaigns();
    }

    @Override
    public RankedParticipantCollection getRankedParticipantsPerCampaign(int campaignID) throws SQLException {
        return new RankedParticipantCollection(
                campaignDAO.getCampaignName(campaignID),
                reportDAO.getRankedParticipantsPerCampaign(campaignID)
        );
    }

    @Override
    public AnswerCollection getAnswersPerParticipant(int campaignID, int participantID) throws SQLException {
        AnswerCollection answerCollection = participantDAO.getFirstAndLastname(participantID);
        answerCollection.setAnswers(reportDAO.getAnswersPerParticipant(campaignID, participantID));
        return answerCollection;
    }
}
