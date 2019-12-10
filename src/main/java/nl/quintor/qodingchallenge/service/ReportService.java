package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.dto.RankedParticipantCollection;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.persistence.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    @Autowired
    void setCampaignDAO(CampaignDAO campaignDAO);

    @Autowired
    void setParticipantDAO(ParticipantDAO participantDAO);

    @Autowired
    void setReportDAO(ReportDAO reportDAO);

    List<CampaignDTO> getAllCampaigns() throws SQLException;

    RankedParticipantCollection getRankedParticipantsPerCampaign(int campaignID) throws SQLException;

    AnswerCollection getAnswersPerParticipant(int campaignID, int participantID) throws SQLException;
}
