package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotSetAnswerException extends CustomException {
    public CouldNotSetAnswerException() {
    }

    public CouldNotSetAnswerException(String message) {
        super(message);
    }

    public CouldNotSetAnswerException(String message, String details) {
        super(message, details);
    }

    public CouldNotSetAnswerException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
