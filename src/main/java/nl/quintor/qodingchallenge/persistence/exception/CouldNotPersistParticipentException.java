package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotPersistParticipentException extends CustomException {
    public CouldNotPersistParticipentException() {
    }

    public CouldNotPersistParticipentException(String message) {
        super(message);
    }

    public CouldNotPersistParticipentException(String message, String details) {
        super(message, details);
    }

    public CouldNotPersistParticipentException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
