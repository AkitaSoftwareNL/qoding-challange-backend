package nl.quintor.qodingchallenge.dto;

import java.util.ArrayList;
import java.util.Objects;

public class AmountOfQuestionTypeCollection {

    public ArrayList<AmountOfQuestionTypeDTO> collection;

    public AmountOfQuestionTypeCollection(ArrayList<AmountOfQuestionTypeDTO> collection) {
        this.collection = collection;
    }

    public ArrayList<AmountOfQuestionTypeDTO> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<AmountOfQuestionTypeDTO> collection) {
        this.collection = collection;
    }

    public int getTotal() {
        int total = 0;

        for (var amountType : collection) {
            total += amountType.amount;
        }

        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountOfQuestionTypeCollection that = (AmountOfQuestionTypeCollection) o;
        return Objects.equals(collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }

    @Override
    public String toString() {
        return "AmountOfQuestionTypeCollection{" +
                "collection=" + collection +
                '}';
    }
}
