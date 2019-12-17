package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerDTO;
import nl.quintor.qodingchallenge.dto.ParticipantDTO;

import java.sql.SQLException;
import java.util.List;

public interface ReportDAO {
    List<AnswerDTO> getAnswersPerParticipant(int campaignID, String participantID) throws SQLException;
}
