package com.liuzq.web;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: springboot-liuzq
 * @Package: com.liuzq.web
 * @ClassName: RedisTestController
 * @Author: liuzq
 * @Description:
 * @Date: 2019/11/27 15:40
 * @Version: 1.0
 */
@RestController
public class RedisTestController {
    static Logger logger = LoggerFactory.getLogger(RedisTestController.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/testR")
    public void get() {
        Object o = setHashKeyAndGet();
        logger.info("获取key:{}", JSON.toJSONString(o));
        logger.info("获取hashkey:{}", JSON.toJSONString(getHashValue()));
    }

    public Object setKeyAndGet(String key, String value) {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(key, value, 1, TimeUnit.MINUTES);//1分钟过期
        return ops.get(key);
    }

    public String getValue(String key) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        return ops.get(key);
    }

    public Object setHashKeyAndGet() {
        HashOperations ops = this.redisTemplate.opsForHash();
        ops.delete("redis", "test4");
        ops.put("redis", "test1", "testRedisHashValue1");
        ops.put("redis", "test2", "testRedisHashValue1");
        ops.put("redis", "test3", "testRedisHashValue3");
        ops.put("redis", "test3", "testRedisHashValue4");
        ops.put("redis", "test5", "testRedisHashValue5");
        redisTemplate.expire("redis", 24, TimeUnit.HOURS);
        return ops.entries("redis");
    }

    public Object getHashValue() {
        HashOperations ops = this.redisTemplate.opsForHash();
        return ops.get("redis", "test1");
    }


}
