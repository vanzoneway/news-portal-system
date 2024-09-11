package by.vanzoneway.newsservicecore.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;


public class LruCache<K, V> implements Cache<K, V> {

    protected final int capacity;
    protected Map<K, Element<K, V>> elementsMap;
    protected LinkedList<Element<K, V>> elementsList;

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.elementsMap = new HashMap<>();
        this.elementsList = new LinkedList<>();
    }


    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            elementsList.remove(elementsMap.get(key));
        } else {
            ensureCapacity();
        }
        final Element<K, V> newElement = new Element<>(key, value);
        elementsMap.put(key, newElement);
        elementsList.addFirst(newElement);

    }

    @Override
    public Optional<V> get(K key) {
        Optional<V> result = Optional.empty();
        if (elementsMap.containsKey(key)) {
            final Element<K, V> element = elementsMap.get(key);
            result = Optional.of(element.value);
            elementsList.remove();
            elementsList.addFirst(element);
        }
        return result;
    }

    @Override
    public void remove(K key) {
        if (containsKey(key)) {
            Element<K, V> element = elementsMap.get(key);
            elementsList.remove(element);
            elementsMap.remove(key);
        }
    }

    @Override
    public Integer size() {
        return elementsList.size();
    }

    @Override
    public Boolean containsKey(K key) {
        return elementsMap.containsKey(key);
    }

    private boolean isNotEnoughSize() {
        return size() == capacity;
    }

    private void ensureCapacity() {
        if (isNotEnoughSize()) {
            final Element<K, V> removedElement = elementsList.removeLast();
            elementsMap.remove(removedElement.key);
        }
    }


    protected static class Element<K, V> {
        protected final K key;
        protected final V value;

        public Element(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
