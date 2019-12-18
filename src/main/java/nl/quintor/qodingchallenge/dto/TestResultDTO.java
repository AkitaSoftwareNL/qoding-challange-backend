package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class TestResultDTO {
    private int totalTests;
    private int totalTestsPassed;
    private int totalTestsFailed;

    public TestResultDTO(int totalTests, int totalTestsPassed, int totalTestsFailed) {
        this.totalTests = totalTests;
        this.totalTestsPassed = totalTestsPassed;
        this.totalTestsFailed = totalTestsFailed;
    }

    public TestResultDTO() {
    }

    public int getTotalTests() {
        return totalTests;
    }

    public int getTotalTestsPassed() {
        return totalTestsPassed;
    }

    public int getTotalTestsFailed() {
        return totalTestsFailed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestResultDTO that = (TestResultDTO) o;
        return totalTests == that.totalTests &&
                totalTestsPassed == that.totalTestsPassed &&
                totalTestsFailed == that.totalTestsFailed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTests, totalTestsPassed, totalTestsFailed);
    }

    @Override
    public String toString() {
        return "TestResultDTO{" +
                "totalTests=" + totalTests +
                ", totalTestsPassed=" + totalTestsPassed +
                ", totalTestsFailed=" + totalTestsFailed +
                '}';
    }
}
