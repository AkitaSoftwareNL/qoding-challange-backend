package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class ValidationException extends CustomException {

    private static final String MESSAGE = "Could not validate code";

    public ValidationException() {
        super(MESSAGE);
    }

    public ValidationException(String details) {
        super(MESSAGE, details);
    }

    public ValidationException(String details, String nextActions) {
        super(MESSAGE, details, nextActions);
    }
}
