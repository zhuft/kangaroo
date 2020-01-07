package com.mobanker.kangaroo.calendar.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JRedisClientFactory {

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.33.50", Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, "daiqian");
    
    public static Jedis getJRedisClient(){
        return pool.getResource();
        
    }
}
