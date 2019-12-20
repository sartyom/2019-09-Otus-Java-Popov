package ru.otus.hw11.cachehw;

/**
 * @author sergey
 * created on 14.12.18.
 */
public interface HwCache<K, V> {
    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(HwCacheListener listener);

    void removeListener(HwCacheListener listener);
}