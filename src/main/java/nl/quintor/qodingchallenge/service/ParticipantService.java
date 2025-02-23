package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import nl.quintor.qodingchallenge.persistence.dao.ParticipantDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public interface ParticipantService {

    @Autowired
    void setParticipantDAO(ParticipantDAO participantDAO);

    ParticipantDTO addParticipant(int campaignID, ParticipantDTO participantDTO) throws SQLException;
}
