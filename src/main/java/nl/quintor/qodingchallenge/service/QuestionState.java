package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.service.exception.IllegalEnumStateException;

import static java.lang.String.format;

public enum QuestionState {
    PENDING(1),
    INCORRECT(3),
    CORRECT(2);

    public final int state;

    QuestionState(int state) {
        this.state = state;
    }

    public static int getEnum(String state) {
        for (QuestionState questionState : values()) {
            if (questionState.toString().equalsIgnoreCase(state)) {
                return questionState.getState();
            }
        }
        throw new IllegalEnumStateException(
                "The state of this question does not exists",
                format("The state you have tried to insert was %s", state),
                "Contact support");
    }

    public int getState() {
        return state;
    }
}
