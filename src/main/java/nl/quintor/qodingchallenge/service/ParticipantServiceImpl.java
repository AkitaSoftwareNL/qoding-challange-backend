package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.service.exception.CouldNotAddParticipantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

import static java.lang.String.format;

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

    @Override
    public void addParticipant(int campaignID, ParticipantDTO participantDTO) throws SQLException {
        participantDTO.setCampaignID(campaignID);
        if(participantDAO.participantAlreadyExists(participantDTO)) {
            throw new CouldNotAddParticipantException(
                    "Participant could not be added to this campaign",
                    format("Participant name = %s %s and ID %d = \n Campaign name = %s", participantDTO.getFirstname(), participantDTO.getLastname(), participantDTO.getCampaignID(), participantDTO.getCampaignID()),
                    "Most likely you have already participated in this campaign. If not contact the support"
            );
        }
        participantDAO.addParticipant(participantDTO);
    }
}
