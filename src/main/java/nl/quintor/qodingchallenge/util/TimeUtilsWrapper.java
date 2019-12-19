package nl.quintor.qodingchallenge.utils;

class TimeUtilsWrapper {

    boolean dateValidate(String inputDate, String format, String... formats) {
        return TimeUtils.dateValidate(inputDate, format, formats);
    }
}
