package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AnswerCollection;

import java.sql.SQLException;

public interface ParticipantDAO {
    AnswerCollection getFirstAndLastname(int participantID) throws SQLException;
}
