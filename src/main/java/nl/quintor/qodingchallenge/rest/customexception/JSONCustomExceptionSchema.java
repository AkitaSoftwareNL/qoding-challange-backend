package nl.quintor.qodingchallenge.rest.customexception;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSONCustomExceptionSchema that = (JSONCustomExceptionSchema) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(details, that.details) &&
                Objects.equals(nextAction, that.nextAction) &&
                Objects.equals(support, that.support);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, details, nextAction, support);
    }
}
