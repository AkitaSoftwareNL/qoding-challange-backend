package nl.quintor.qodingchallenge.utils;

import org.apache.commons.validator.GenericValidator;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void getTimeStampReturnsTimeStampInRightFormat() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(TimeUtils.getTimeStamp() + "000000");
        assertTrue(date.equals(new Date(TimeUtils.getTimeStamp())));
    }
}