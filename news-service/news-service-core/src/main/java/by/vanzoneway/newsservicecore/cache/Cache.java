package by.vanzoneway.newsservicecore.cache;

import java.util.Optional;

public interface Cache <K, V>{

    void put(K key, V value);

    Optional<V> get(K key);

    Integer size();

    Boolean containsKey(K key);

    void remove(K key);
}
