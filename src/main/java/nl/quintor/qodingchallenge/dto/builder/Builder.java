package nl.quintor.qodingchallenge.dto.builder;

import java.sql.SQLException;

@FunctionalInterface
public interface Builder<T> {
    void accept(T t) throws SQLException;
}
