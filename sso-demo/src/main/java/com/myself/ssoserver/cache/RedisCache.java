package com.myself.ssoserver.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Created by zion
 * @Date 2019/8/29.
 */
@Slf4j
@Component
public class RedisCache {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }

    private static final String SEND_RECOED_MSG_PREFIX = "send_msg_record_";

    /**
     * 限定手机号1分钟只能发送1次，5分钟最多发送3次
     *
     * @param mobilePhone
     * @return
     */
    public static Boolean verifySendRecordMsg(String mobilePhone) {
        try {
            RedisAtomicLong oneMinutesCounter = new RedisAtomicLong(SEND_RECOED_MSG_PREFIX + mobilePhone, redisTemplate.getConnectionFactory(), 0L);
            oneMinutesCounter.expire(1, TimeUnit.MINUTES);

            RedisAtomicLong fiveMinutesCounter = new RedisAtomicLong(SEND_RECOED_MSG_PREFIX + mobilePhone, redisTemplate.getConnectionFactory(), 0L);
            fiveMinutesCounter.expire(5, TimeUnit.MINUTES);

            return !(oneMinutesCounter.getAndIncrement() > 0L || fiveMinutesCounter.getAndIncrement() > 3);
        } catch (Exception e) {
            log.error(">>>>> 注册发送短信验证码频率限制功能异常");
        }
        return true;
    }
}
