package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantDAO {
    AnswerCollection getFirstAndLastname(String participantID);

    List<ParticipantDTO> getRankedParticipantsPerCampaign(int campaignID);

    String addParticipant(ParticipantDTO participantDTO, int campaignID);

    boolean participantHasAlreadyParticipatedInCampaign(ParticipantDTO participantDTO, int campaignID) throws SQLException;

    void addTimeToParticipant(String participantID);
}
