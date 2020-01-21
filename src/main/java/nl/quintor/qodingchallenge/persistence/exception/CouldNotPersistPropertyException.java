package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotPersistPropertyException extends CustomException {
    public CouldNotPersistPropertyException() {
    }

    public CouldNotPersistPropertyException(String message) {
        super(message);
    }

    public CouldNotPersistPropertyException(String message, String details) {
        super(message, details);
    }

    public CouldNotPersistPropertyException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
