package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotPersistQuestionException extends CustomException {
    public CouldNotPersistQuestionException() {
    }

    public CouldNotPersistQuestionException(String message) {
        super(message);
    }

    public CouldNotPersistQuestionException(String message, String details) {
        super(message, details);
    }

    public CouldNotPersistQuestionException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
