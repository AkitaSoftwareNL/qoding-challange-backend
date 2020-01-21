package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotRecievePropertyException extends CustomException {
    public CouldNotRecievePropertyException() {
    }

    public CouldNotRecievePropertyException(String message) {
        super(message);
    }

    public CouldNotRecievePropertyException(String message, String details) {
        super(message, details);
    }

    public CouldNotRecievePropertyException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
