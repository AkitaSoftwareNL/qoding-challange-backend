package nl.quintor.qodingchallenge.utils;

import java.time.LocalDateTime;

import static java.lang.String.format;

public class TimeUtils {

    public static String getTimeStamp() {
        LocalDateTime now = LocalDateTime.now();

        final int year = now.getYear();
        final int month = now.getMonthValue();
        final int day = now.getDayOfMonth();
        final int hour = now.getHour();
        final int minute = now.getMinute();
        final int second = now.getSecond();

        return format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }
}
