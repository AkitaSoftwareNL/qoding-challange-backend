package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotAddParticipantException extends CustomException {
    public CouldNotAddParticipantException(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
