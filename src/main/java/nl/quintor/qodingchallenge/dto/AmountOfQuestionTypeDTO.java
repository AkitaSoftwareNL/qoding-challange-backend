package nl.quintor.qodingchallenge.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AmountOfQuestionTypeDTO {
    public String type;
    public int amount;

    public AmountOfQuestionTypeDTO() {
    }

    public AmountOfQuestionTypeDTO(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public AmountOfQuestionTypeDTO(ResultSet resultset) throws SQLException {
        this.amount = resultset.getInt("Amount");
        this.type = resultset.getString("Type");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountOfQuestionTypeDTO that = (AmountOfQuestionTypeDTO) o;
        return amount == that.amount &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount);
    }

    @Override
    public String toString() {
        return "AmountOfQuestionType{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }
}
