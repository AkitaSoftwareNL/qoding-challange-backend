package nl.quintor.qodingchallenge.percistence.exception;

public class CampaignAlreadyExistsException extends RuntimeException {
    public CampaignAlreadyExistsException(String s) {
        super(s);
    }
}
