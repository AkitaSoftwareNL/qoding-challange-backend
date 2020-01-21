package nl.quintor.qodingchallenge.persistence.exception;

import nl.quintor.qodingchallenge.rest.customexception.CustomException;

public class ParticipentHasAlreadyParticipatedInCampaignException extends CustomException {
    public ParticipentHasAlreadyParticipatedInCampaignException() {
    }

    public ParticipentHasAlreadyParticipatedInCampaignException(String message) {
        super(message);
    }

    public ParticipentHasAlreadyParticipatedInCampaignException(String message, String details) {
        super(message, details);
    }

    public ParticipentHasAlreadyParticipatedInCampaignException(String message, String details, String nextActions) {
        super(message, details, nextActions);
    }
}
