package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.service.exception.IllegalEnumStateException;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public enum QuestionType {
    OPEN(1),
    MULTIPLE(2),
    PROGRAM(3),
    TOTAL(4);

    private final int state;

    QuestionType(int state) {
        this.state = state;
    }

    public static int getEnumAsInt(String state) {
        for (QuestionType questionType : values()) {
            if (questionType.toString().equalsIgnoreCase(state)) {
                return questionType.getState();
            }
        }
        throw new IllegalEnumStateException(
                "The state of this question does not exists",
                format("The state you have tried to insert was %s", state),
                "Contact support");
    }

    /**
     * @param type String of the enum
     * @return the enum its string value to lower case
     * @deprecated since 0.1
     */
    @Deprecated
    public static String getEnumAsString(String type) {
        List<QuestionType> types = Arrays.asList(QuestionType.values());
        return types.stream()
                .filter(questionType -> questionType.toString().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalEnumStateException(
                                "The question type u have entered does not exist",
                                format("The question type of question type: %s does not exist", type),
                                "Contact support"
                        )
                ).toString();
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
