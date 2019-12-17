package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantDAO {
    AnswerCollection getFirstAndLastname(String participantID) throws SQLException;

    void addParticipantToCampaign(int campaignID, String participantID) throws SQLException;

    List<ParticipantDTO> getParticipantsPerCampaign(int campaignID) throws SQLException;

    void addParticipant(ParticipantDTO participantDTO, int campaignID) throws SQLException;

    boolean participantHasAlreadyParticipatedInCampaign(ParticipantDTO participantDTO, int campaignID) throws SQLException;
}
