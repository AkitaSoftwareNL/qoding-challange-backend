package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private ParticipantDAO participantDAO;

    @Override
    @Autowired
    public void setParticipantDAO(ParticipantDAO participantDAO) {
        this.participantDAO = participantDAO;
    }

    @Override
    public void addParticipantToCampaign(int campaignID, int participantID) throws SQLException {
        participantDAO.addParticipantToCampaign(campaignID, participantID);
    }
}
