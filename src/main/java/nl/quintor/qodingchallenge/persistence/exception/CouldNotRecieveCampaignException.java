package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotRecieveCampaignException extends CustomException {
    public CouldNotRecieveCampaignException() {
    }

    public CouldNotRecieveCampaignException(String message) {
        super(message);
    }

    public CouldNotRecieveCampaignException(String message, String details) {
        super(message, details);
    }

    public CouldNotRecieveCampaignException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
