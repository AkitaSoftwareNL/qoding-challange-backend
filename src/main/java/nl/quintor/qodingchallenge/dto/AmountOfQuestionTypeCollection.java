package nl.quintor.qodingchallenge.dto;

import java.util.Arrays;

public class AmountOfQuestionTypeCollection {

    public AmountOfQuestionType[] collection;

    public AmountOfQuestionTypeCollection(AmountOfQuestionType[] collection) {
        this.collection = collection;
    }

    public AmountOfQuestionType[] getCollection() {
        return collection;
    }

    public void setCollection(AmountOfQuestionType[] collection) {
        this.collection = collection;
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
