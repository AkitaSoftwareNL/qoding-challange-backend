package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CampaignDoesNotExistsException extends CustomException {
    public CampaignDoesNotExistsException(String message, String detail, String nextAction) {
        super(message, detail, nextAction);
    }
}
