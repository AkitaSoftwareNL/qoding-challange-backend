package nl.quintor.qodingchallenge.service.exception;

public class NoCampaignFoundException extends RuntimeException {
    public NoCampaignFoundException(String s) {
        super(s);
    }
}
