package by.vanzoneway.newsservicecore.cache;


import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

public class LfuCache<K, V> implements Cache<K, V> {

    private final int capacity;
    protected Map<K, Element<K, V>> elementsMap;
    protected Map<Integer, LinkedHashSet<Element<K, V>>> frequencyMap;
    private int minFrequency;

    public LfuCache(int capacity) {
        this.capacity = capacity;
        this.elementsMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.minFrequency = 1;
    }

    @Override
    public void put(K key, V value) {
        if (capacity <= 0) return;

        if (elementsMap.containsKey(key)) {
            Element<K, V> existingElement = elementsMap.get(key);
            existingElement.value = value;
            updateFrequency(existingElement);
        } else {

            if (elementsMap.size() >= capacity) {
                removeLeastFrequent();
            }
            Element<K, V> newElement = new Element<>(key, value);
            elementsMap.put(key, newElement);
            frequencyMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(newElement);
            minFrequency = 1;
        }
    }

    @Override
    public Optional<V> get(K key) {
        if (!elementsMap.containsKey(key)) {
            return Optional.empty();
        }
        Element<K, V> element = elementsMap.get(key);
        updateFrequency(element);
        return Optional.of(element.value);
    }

    @Override
    public void remove(K key) {
        if (elementsMap.containsKey(key)) {
            Element<K, V> element = elementsMap.get(key);
            frequencyMap.get(element.frequency).remove(element);
            if (frequencyMap.get(element.frequency).isEmpty() && element.frequency == minFrequency) {
                minFrequency++;
            }
            elementsMap.remove(key);
        }
    }

    @Override
    public Integer size() {
        return elementsMap.size();
    }

    @Override
    public Boolean containsKey(K key) {
        return elementsMap.containsKey(key);
    }

    private void updateFrequency(Element<K, V> element) {
        int oldFrequency = element.frequency;
        element.frequency++;

        frequencyMap.get(oldFrequency).remove(element);
        if (frequencyMap.get(oldFrequency).isEmpty()) {
            frequencyMap.remove(oldFrequency);
            if (oldFrequency == minFrequency) {
                minFrequency++;
            }
        }

        frequencyMap.computeIfAbsent(element.frequency, k -> new LinkedHashSet<>()).add(element);
    }

    private void removeLeastFrequent() {
        LinkedHashSet<Element<K, V>> leastFrequentElements = frequencyMap.get(minFrequency);
        if (leastFrequentElements != null && !leastFrequentElements.isEmpty()) {
            Element<K, V> elementToRemove = leastFrequentElements.iterator().next();
            leastFrequentElements.remove(elementToRemove);
            elementsMap.remove(elementToRemove.key);
        }
    }

    protected static class Element<K, V> {
        protected final K key;
        protected V value;
        protected int frequency;

        public Element(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }
}