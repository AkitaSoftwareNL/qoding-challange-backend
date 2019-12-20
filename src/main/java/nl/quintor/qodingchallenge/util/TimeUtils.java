package nl.quintor.qodingchallenge.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static java.lang.String.format;

public final class TimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);

    private TimeUtils() {
    }

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

    public static boolean dateValidate(String inputDate, String format, String... formats) {
        String[] datePattern = new String[formats.length + 1];
        try {
            datePattern[0] = format;
            if (formats.length - 1 >= 0) {
                System.arraycopy(formats, 1, datePattern, 1, formats.length - 1);
            }
            for (String pattern : datePattern) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date date = sdf.parse(inputDate);
                String formattedDate = sdf.format(date);
                if (inputDate.equals(formattedDate)) {
                    return true;
                }
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
