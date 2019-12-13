package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantDAO {
    AnswerCollection getFirstAndLastname(int participantID) throws SQLException;

    void addParticipantToCampaign(int campaignID, int participantID) throws SQLException;

    List<ParticipantDTO> getParticipantsPerCampaign(int campaignID) throws SQLException;

    void addParticipant(ParticipantDTO participantDTO) throws SQLException;

    boolean participantHasAlreadyParticipatedInCampaign(ParticipantDTO participantDTO) throws SQLException;
}
