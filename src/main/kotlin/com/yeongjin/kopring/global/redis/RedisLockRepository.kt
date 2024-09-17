package com.yeongjin.kopring.global.redis

import com.yeongjin.kopring.global.exception.CustomException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun lock(key: Long): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent(key.toString(), "lock", Duration.ofMillis(3000)) ?: false
    }

    fun unlock(key: Long): Boolean {
        return redisTemplate.delete(key.toString())
    }
}