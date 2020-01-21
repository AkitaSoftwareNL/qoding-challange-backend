package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotUpdateStateException extends CustomException {
    public CouldNotUpdateStateException() {
    }

    public CouldNotUpdateStateException(String message) {
        super(message);
    }

    public CouldNotUpdateStateException(String message, String details) {
        super(message, details);
    }

    public CouldNotUpdateStateException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
