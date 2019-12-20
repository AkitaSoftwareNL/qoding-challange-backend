package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class IllegalEnumStateException extends CustomException {
    public IllegalEnumStateException(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
