package com.kozich.userservice.service.impl;

import com.kozich.userservice.service.api.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl<K, V> implements CacheService<K, V> {

    private final RedisTemplate<K, V> redisTemplate;

    public CacheServiceImpl(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveWithTTL(K key, V value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public void save(K key, V value) {
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public V deleteAndGet(K key) {
        V value = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return value;
    }

}

