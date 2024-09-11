package by.vanzoneway.newsservicecore.cache.concurrent;

import by.vanzoneway.newsservicecore.cache.Cache;

import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static by.vanzoneway.newsservicecore.cache.concurrent.Guard.guardedBy;

public class ConcurrentCache<K, V> implements Cache<K, V> {

    private final Cache cache;
    private final ReadWriteLock readWriteLock;

    public ConcurrentCache(Cache<K, V> cache) {
        this.cache = cache;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public Optional<V> get(K key) {
        return guardedBy(readWriteLock).executeWrite(() -> cache.get(key));
    }

    @Override
    public void put(K key, V value) {
        guardedBy(readWriteLock).executeWrite(() -> cache.put(key, value));
    }

    @Override
    public Boolean containsKey(K key) {
        return guardedBy(readWriteLock).executeRead(() -> cache.containsKey(key));
    }

    @Override
    public void remove(K key) {
        guardedBy(readWriteLock).executeWrite(() -> cache.remove(key));
    }

    @Override
    public Integer size() {
        return guardedBy(readWriteLock).executeRead(() -> cache.size());
    }


}