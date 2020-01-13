package nl.quintor.qodingchallenge.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class HashMapUtils {

    private HashMapUtils() {
    }

    /**
     * <p>Checks if the key already exists in an HashMap
     * For use please stream the HashMap and call the filter method,
     * inside the filter method you can call this method to find duplicate keys.
     *
     * @example HashMap.stream()
     *                 .filter(HashMapUtils.distinctByKey(Class::Method))
     *
     * @param keyExtractor Object which contains the key
     * @param <T>          Method call to get the key
     * @return true if the key exists in the HashMap
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
