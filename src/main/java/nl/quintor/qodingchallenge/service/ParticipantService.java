package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public interface ParticipantService {

    @Autowired
    void setParticipantDAO(ParticipantDAO participantDAO);

    @Autowired
    void setCampaignDAO(CampaignDAO campaignDAO);

    void addParticipantToCampaign(int campaignID, String participantID) throws SQLException;

    void addParticipant(int campaignID, ParticipantDTO participantDTO) throws SQLException;
}
