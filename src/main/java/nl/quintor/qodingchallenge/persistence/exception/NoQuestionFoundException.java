package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class NoQuestionFoundException extends CustomException {
    public NoQuestionFoundException(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
