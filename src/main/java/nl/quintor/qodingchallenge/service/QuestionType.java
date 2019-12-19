package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.service.exception.IllegalEnumStateException;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public enum QuestionType {
    OPEN,
    MULTIPLE,
    PROGRAM;

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

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
