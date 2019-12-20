package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.service.exception.IllegalEnumStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionStateTest {

    @Test
    void enumReturnsCorrespondingValue() {
        assertEquals(QuestionState.PENDING.getState(), 1);
        assertEquals(QuestionState.CORRECT.getState(), 2);
        assertEquals(QuestionState.INCORRECT.getState(), 3);
    }

    @Test
    void enumValueOfReturnsRightState() {
        assertEquals(QuestionState.getEnum("pending"), 1);
    }

    @Test
    void enumValueOFThrowsIllegalEnumStateException() {
        assertThrows(IllegalEnumStateException.class, () -> QuestionState.getEnum("DoesNotExist"));
    }
}