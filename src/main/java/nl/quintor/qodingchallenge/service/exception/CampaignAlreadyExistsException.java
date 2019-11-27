package nl.quintor.qodingchallenge.service.exception;

public class CampaignAlreadyExistsException extends RuntimeException {
    public CampaignAlreadyExistsException(String s) {
        super(s);
    }
}
