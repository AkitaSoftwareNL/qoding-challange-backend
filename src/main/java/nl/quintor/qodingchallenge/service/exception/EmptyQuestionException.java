package nl.quintor.qodingchallenge.service.exception;

public class EmptyQuestionException extends RuntimeException {
    public EmptyQuestionException(String s) {
        super(s);
    }
}
