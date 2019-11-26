package com.wzlue.common.utils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email wzlue.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtils {
    private static RedisUtils single;
    @PostConstruct
    public void init(){
        single = this;
    }
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;
    private final static Gson gson = new Gson();

    public static void set(String key, Object value, long expire){
        single.valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            single.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public static void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public static <T> T get(String key, Class<T> clazz, long expire) {
        String value = single.valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            single.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public static <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public static String get(String key, long expire) {
        String value = single.valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            single.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public static String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public static void delete(String key) {
        single.redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private static String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private static <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }
}
