package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class CouldNotPersistCampaignException extends CustomException {
    public CouldNotPersistCampaignException() {
    }

    public CouldNotPersistCampaignException(String message) {
        super(message);
    }

    public CouldNotPersistCampaignException(String message, String details) {
        super(message, details);
    }

    public CouldNotPersistCampaignException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
