package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class EmptyQuestionException extends CustomException {
    public EmptyQuestionException(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
