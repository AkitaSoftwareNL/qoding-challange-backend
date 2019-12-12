package nl.quintor.qodingchallenge.rest.customexception;

public class JSONCustomExceptionSchema {

    private String message;
    private String details;
    private String nextAction;
    private String support;

    public JSONCustomExceptionSchema(String message, String details, String nextAction, String support) {
        this.message = message;
        this.details = details;
        this.nextAction = nextAction;
        this.support = support;
    }

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

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
