package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class AnswerNotFoundException extends CustomException {
    public AnswerNotFoundException(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
