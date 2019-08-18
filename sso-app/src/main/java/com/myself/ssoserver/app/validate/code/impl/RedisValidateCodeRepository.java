package com.myself.ssoserver.app.validate.code.impl;


import com.myself.ssoserver.validate.ValidateCode;
import com.myself.ssoserver.validate.ValidateCodeException;
import com.myself.ssoserver.validate.ValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 使用redis替代系统中session的存储
 * 图片验证码，短信验证码等等
 *
 * @author Created by zion
 * @Date 2019/2/19.
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, String key, ValidateCode code) {
        redisTemplate.opsForValue().set(buildKey(request, key), code, 30, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, String key) {
        return (ValidateCode) redisTemplate.opsForValue().get(buildKey(request, key));
    }

    @Override
    public void remove(ServletWebRequest request, String key) {
        redisTemplate.delete(buildKey(request, key));

    }

    private String buildKey(ServletWebRequest request, String key) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return key + "_" + deviceId;
    }
}
