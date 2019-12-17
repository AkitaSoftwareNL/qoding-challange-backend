package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.dto.builder.ParticipantDTOBuilder;
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
    public ParticipantDTO addParticipant(int campaignID, ParticipantDTO participantDTO) throws SQLException {
        if (participantDAO.participantHasAlreadyParticipatedInCampaign(participantDTO, campaignID)) {
            throw new CouldNotAddParticipantException(
                    "Participant could not be added to this campaign",
                    format("Participant name = %s %s with ID %d already exists in this campaign with campaign id = %d", participantDTO.getFirstname(), participantDTO.getLastname(), campaignID, participantDTO.getCampaignID()),
                    "Most likely you have already participated in this campaign. If not contact support"
            );
        }
        return new ParticipantDTOBuilder().with(participantDTOBuilder ->
                participantDTOBuilder.participantID = participantDAO.addParticipant(participantDTO, campaignID))
                .build();
    }
}
