package nl.quintor.qodingchallenge.persistence.exception;

public class CampaignAlreadyExistsException extends RuntimeException {
    public CampaignAlreadyExistsException(String s) {
        super(s);
    }
}
