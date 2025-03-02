package com.kozich.userservice.service.api;

import java.util.concurrent.TimeUnit;

public interface CacheService<K, V> {

    void saveWithTTL(K key, V value, long timeout, TimeUnit timeUnit);

    void save(K key, V value);

    V get(K key);

    void delete(K key);

    V deleteAndGet(K key);

}