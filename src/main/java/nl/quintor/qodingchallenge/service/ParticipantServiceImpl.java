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
                    format("Deelnemer %s %s heeft al mee gedaan met deze campagne. Als dit nog niet het geval is neem contact op met support.", participantDTO.getFirstname(), participantDTO.getLastname()),
                    format("Deelnemer naam = %s %s met ID %d heeft al meegedaan met campagne id = %d", participantDTO.getFirstname(), participantDTO.getLastname(), campaignID, participantDTO.getCampaignID()),
                    "Het geval kan zijn dat u al heeft mee gedaan, als dit niet het geval is neem contact op met support"
            );
        }
        return new ParticipantDTOBuilder().with(participantDTOBuilder ->
                participantDTOBuilder.participantID = participantDAO.addParticipant(participantDTO, campaignID))
                .build();
    }
}
