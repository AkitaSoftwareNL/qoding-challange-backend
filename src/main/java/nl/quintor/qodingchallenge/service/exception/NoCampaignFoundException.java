package nl.quintor.qodingchallenge.service.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class NoCampaignFoundException extends CustomException {
    public NoCampaignFoundException(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
