package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CannotPersistQuestionException extends CustomException {
    public CannotPersistQuestionException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
