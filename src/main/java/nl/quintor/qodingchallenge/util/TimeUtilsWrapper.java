package nl.quintor.qodingchallenge.util;

public class TimeUtilsWrapper {

    public boolean dateValidate(String inputDate, String format, String... formats) {
        return TimeUtils.dateValidate(inputDate, format, formats);
    }
}
