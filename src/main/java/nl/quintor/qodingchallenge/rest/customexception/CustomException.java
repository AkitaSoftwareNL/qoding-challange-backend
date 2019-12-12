package nl.quintor.qodingchallenge.rest.customexception;

import java.sql.SQLException;

public class CustomException extends RuntimeException {

    private String message;
    private String details;
    private String nextActions;
    private String support;

    protected CustomException() {}

    public CustomException(String message, String details, String nextActions) {
        this.message = message;
        this.details = details;
        this.nextActions = nextActions;
        support = "https://quintor.nl/";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNextActions() {
        return nextActions;
    }

    public void setNextActions(String nextActions) {
        this.nextActions = nextActions;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
