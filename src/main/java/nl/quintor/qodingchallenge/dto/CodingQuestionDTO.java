package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class CodingQuestionDTO {

    private long maxExecutionTime;
    private String code;
    private String test;

    public CodingQuestionDTO() {
        this.maxExecutionTime = 0;
        this.code = "";
        this.test = "";
    }

    public CodingQuestionDTO(String code, String test) {
        new CodingQuestionDTO(10, code, test);
    }

    public CodingQuestionDTO(long maxExecutionTime, String code, String test) {
        this.maxExecutionTime = maxExecutionTime;
        this.code = code;
        this.test = test;
    }

    public long getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public String getCode() {
        return code;
    }

    public String getTest() {
        return test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodingQuestionDTO that = (CodingQuestionDTO) o;
        return maxExecutionTime == that.maxExecutionTime &&
                Objects.equals(code, that.code) &&
                Objects.equals(test, that.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxExecutionTime, code, test);
    }

    @Override
    public String toString() {
        return "CodingQuestionDTO{" +
                "maxExecutionTime=" + maxExecutionTime +
                ", code='" + code + '\'' +
                ", testCode='" + test + '\'' +
                '}';
    }
}
