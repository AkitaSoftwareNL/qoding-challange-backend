package nl.quintor.qodingchallenge.dto;

import java.util.Arrays;

public class AmountOfQuestionTypeCollection {

    public AmountOfQuestionTypeDTO[] collection;

    public AmountOfQuestionTypeCollection(AmountOfQuestionTypeDTO[] collection) {
        this.collection = collection;
    }

    public AmountOfQuestionTypeDTO[] getCollection() {
        return collection;
    }

    public void setCollection(AmountOfQuestionTypeDTO[] collection) {
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
        return Arrays.equals(collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(collection);
    }

    @Override
    public String
    toString() {
        return "AmountOfQuestionTypeCollection{" +
                "collection=" + Arrays.toString(collection) +
                '}';
    }
}
