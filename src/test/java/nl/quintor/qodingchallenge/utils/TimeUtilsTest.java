package nl.quintor.qodingchallenge.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

class TimeUtilsTest {

    @Mock
    private TimeUtilsWrapper sut = spy(new TimeUtilsWrapper());

    @Test
    void getTimeStampReturnsTimeStampInRightFormat() {
        assertTrue(TimeUtils.dateValidate(TimeUtils.getTimeStamp(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
    }

    @Test
    void throwsParseException() {
        given(sut.dateValidate(TimeUtils.getTimeStamp(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd")).willAnswer(invocationOnMock -> {
            throw new ParseException("", 1);
        });

        assertThrows(ParseException.class, () -> sut.dateValidate(TimeUtils.getTimeStamp(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
    }
}