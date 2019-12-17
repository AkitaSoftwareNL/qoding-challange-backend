package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import nl.quintor.qodingchallenge.service.exception.CampaignDoesNotExistsException;
import nl.quintor.qodingchallenge.service.exception.CouldNotAddParticipantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

import static java.lang.String.format;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private ParticipantDAO participantDAO;
    private CampaignDAO campaignDAO;

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
    public void addParticipantToCampaign(int campaignID, String participantID) throws SQLException {
        if (!campaignDAO.campaignExists(campaignID)) {
            throw new CampaignDoesNotExistsException(
                    "The campaign does not exist",
                    "The campaign you are trying to enter has expired",
                    "If the campaign has not yet expired please contact support"
            );
        }
        participantDAO.addParticipantToCampaign(campaignID, participantID);
    }

    @Override
    public void addParticipant(int campaignID, ParticipantDTO participantDTO) throws SQLException {
        if (participantDAO.participantHasAlreadyParticipatedInCampaign(participantDTO, campaignID)) {
            throw new CouldNotAddParticipantException(
                    "Participant could not be added to this campaign",
                    format("Participant name = %s %s with ID %d already exists in this campaign with campaign id = %d", participantDTO.getFirstname(), participantDTO.getLastname(), participantDTO.getCampaignID(), participantDTO.getCampaignID()),
                    "Most likely you have already participated in this campaign. If not contact support"
            );
        }
        participantDAO.addParticipant(participantDTO, campaignID);
    }
}
