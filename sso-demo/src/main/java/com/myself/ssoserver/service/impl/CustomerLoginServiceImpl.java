package com.myself.ssoserver.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.myself.ssoserver.cache.RedisCache;
import com.myself.ssoserver.mapper.CustomerLoginMapper;
import com.myself.ssoserver.model.CustomerLogin;
import com.myself.ssoserver.service.CustomerLoginService;
import com.myself.ssoserver.support.ApiResponse;
import com.myself.ssoserver.support.StatusEnum;
import com.myself.ssoserver.validate.ValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * @author Created by zion
 * @Date 2019/8/28.
 */
@Slf4j
@Service
public class CustomerLoginServiceImpl implements CustomerLoginService {

    @Autowired
    private CustomerLoginMapper customerLoginMapper;

    @Resource(name = "smsValidateCodeProcessor")
    private ValidateCodeProcessor smsValidateCodeProcessor;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public ApiResponse checkMobile(String mobilePhone) {
        CustomerLogin customerLogin = customerLoginMapper.selectOne(Wrappers.<CustomerLogin>lambdaQuery()
            .and(obj -> obj.eq(CustomerLogin::getMobilePhone, mobilePhone)));
        if (customerLogin != null) {
            return ApiResponse.ofStatus(StatusEnum.EXIST_MOBILE_PHONE);
        }
        return ApiResponse.ofSuccess();
    }

    @Override
    public ApiResponse sendMsgCode(ServletWebRequest request, String mobilePhone) {
        if (!RedisCache.verifySendRecordMsg(mobilePhone)) {
            return ApiResponse.ofStatus(StatusEnum.SEND_MSG_TOO_FAST);
        }
        try {
            smsValidateCodeProcessor.create(request);
        } catch (Exception e) {
            log.error(">>>>> 短信验证码发送服务异常,尝试重复调用");
            try {
                smsValidateCodeProcessor.create(request);
            } catch (Exception e1) {
                log.error(">>>>> 短信验证码发送服务异常,第一次重复调用失败");
                try {
                    smsValidateCodeProcessor.create(request);
                } catch (Exception e2) {
                    log.error(">>>>> 短信验证码发送服务异常,第二次重复调用失败");
                }
            }
        }
        return ApiResponse.ofSuccess();
    }
}
