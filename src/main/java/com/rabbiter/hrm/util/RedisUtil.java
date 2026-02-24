package com.rabbiter.hrm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author 李鑫
 * @date 2026/2/24
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // ==================== 通用操作 ====================

    /**
     * 存入数据
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存入数据并设置过期时间
     * @param timeout 过期时间（秒）
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 存入数据并设置过期时间
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取数据
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除数据
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     * @param timeout 过期时间（秒）
     */
    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取过期时间
     * @return 剩余过期时间（秒），-1表示永不过期，-2表示key不存在
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 递增
     * @param delta 递增步长
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param delta 递减步长
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // ==================== Hash操作 ====================

    /**
     * 存入Hash
     */
    public void hSet(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量存入Hash
     */
    public void hPutAll(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取Hash值
     */
    public String hGet(String key, String hashKey) {
        Object value = redisTemplate.opsForHash().get(key, hashKey);
        return value != null ? value.toString() : null;
    }

    /**
     * 获取整个Hash
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除Hash中的字段
     */
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 判断Hash中是否有该字段
     */
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Hash递增
     */
    public Long hIncrement(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 获取Hash大小
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    // ==================== List操作 ====================

    /**
     * 从左边推入列表
     */
    public Long lLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 从左边批量推入列表
     */
    public Long lLeftPushAll(String key, Collection<String> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 从右边推入列表
     */
    public Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 从右边批量推入列表
     */
    public Long lRightPushAll(String key, Collection<String> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 从左边弹出列表
     */
    public String lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从右边弹出列表
     */
    public String lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取列表指定范围内的值
     * @param start 开始索引，0表示第一个
     * @param end 结束索引，-1表示最后一个
     */
    public List<String> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取列表长度
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据索引获取列表中的值
     */
    public String lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 移除列表中指定个数的值
     * @param count 移除个数
     * @param value 要移除的值
     */
    public Long lRemove(String key, long count, String value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 裁剪列表
     * @param start 开始索引
     * @param end 结束索引
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    // ==================== Set操作 ====================

    /**
     * 向Set中添加元素
     */
    public Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取Set中的所有元素
     */
    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断元素是否在Set中
     */
    public Boolean sIsMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取Set的大小
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除Set中的元素
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 随机获取Set中的一个元素
     */
    public String sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取Set中指定个数的元素
     */
    public List<String> sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 求两个Set的交集
     */
    public Set<String> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 求两个Set的并集
     */
    public Set<String> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 求两个Set的差集
     */
    public Set<String> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    // ==================== ZSet（有序集合）操作 ====================

    /**
     * 向ZSet中添加元素
     * @param score 分值
     */
    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 批量添加
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * 获取ZSet中指定范围内的元素（按分数升序）
     * @param start 开始索引
     * @param end 结束索引
     */
    public Set<String> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取ZSet中指定范围内的元素（按分数降序）
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取ZSet中指定分数范围内的元素
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 获取元素的分数
     */
    public Double zScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 获取ZSet的大小
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 统计指定分数范围内的元素个数
     */
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 增加元素的分数
     */
    public Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 移除元素
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 移除指定排名范围内的元素
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 移除指定分数范围内的元素
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取元素的排名（按分数升序，从0开始）
     */
    public Long zRank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 获取元素的排名（按分数降序）
     */
    public Long zReverseRank(String key, String value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    // ==================== 分布式锁 ====================

    /**
     * 尝试获取分布式锁
     * @param key 锁的key
     * @param value 锁的值（建议使用UUID）
     * @param timeout 超时时间（秒）
     * @return 是否获取成功
     */
    public Boolean tryLock(String key, String value, long timeout) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        return success != null && success;
    }

    /**
     * 释放分布式锁
     * @param key 锁的key
     * @param value 锁的值
     * @return 是否释放成功
     */
    public Boolean releaseLock(String key, String value) {
        String currentValue = get(key);
        if (value.equals(currentValue)) {
            return delete(key);
        }
        return false;
    }

    // ==================== 其他常用方法 ====================

    /**
     * 获取指定前缀的keys
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 清空当前数据库
     */
    public void flushDb() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    /**
     * 清空所有数据库
     */
    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    /**
     * 获取RedisTemplate实例
     */
    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}