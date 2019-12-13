package nl.quintor.qodingchallenge.rest.customexception;

public class CustomExceptionWrapper extends CustomException {
    public CustomExceptionWrapper(String message, String details, String nextAction) {
        super(message, details, nextAction);
    }
}
