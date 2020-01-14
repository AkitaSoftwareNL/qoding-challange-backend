package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.service.exception.IllegalEnumStateException;

import static java.lang.String.format;

/**
 * <p>The state a question has, corresponding with the values used in the database.<br>
 * ENUM STATES:
 * <ul>
 *     <li>PENDING = 1</li>
 *     <li>INCORRECT = 2</li>
 *     <li>CORRECT = 3</li>
 * </ul>
 * <strong>When another database is used please check if the states used are still correct</strong>
 */
public enum QuestionState {
    PENDING(1),
    INCORRECT(3),
    CORRECT(2);

    private final int state;

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
