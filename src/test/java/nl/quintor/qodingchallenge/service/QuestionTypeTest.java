package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.service.exception.IllegalEnumStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionTypeTest {

    @Test
    void enumReturnsQuestionTypesToLowerCaseWithToString() {
        assertEquals("multiple", QuestionType.MULTIPLE.toString());
        assertEquals("open", QuestionType.OPEN.toString());
        assertEquals("program", QuestionType.PROGRAM.toString());
    }

    @Test
    void enumGetEnumAsStringReturnsRightEnumAsString() {
        assertEquals("program", QuestionType.getEnumAsString(QuestionType.PROGRAM.toString()));
        assertEquals("open", QuestionType.getEnumAsString(QuestionType.OPEN.toString()));
        assertEquals("multiple", QuestionType.getEnumAsString(QuestionType.MULTIPLE.toString()));
    }

    @Test
    void enumGetEnumAsStringThrowsIllegalEnumStateException() {
        assertThrows(IllegalEnumStateException.class, () -> QuestionType.getEnumAsString("some non working string"));
    }
}