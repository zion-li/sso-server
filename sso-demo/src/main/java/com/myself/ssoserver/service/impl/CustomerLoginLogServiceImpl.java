package com.myself.ssoserver.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.myself.ssoserver.app.service.CustomerLoginLogService;
import com.myself.ssoserver.mapper.CustomerLoginMapper;
import com.myself.ssoserver.model.CustomerLogin;
import com.myself.ssoserver.properties.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by zion
 * @Date 2019/8/21.
 */
@Slf4j
@Service
public class CustomerLoginLogServiceImpl implements CustomerLoginLogService {

    @Autowired
    private CustomerLoginMapper customerLoginMapper;

    @Override
    public void recordLoginSuccess(String url, String userId, String ip) {
        log.warn(">>>>> 用户:{},IP:{}登录成功", userId, ip);

        if (StringUtils.equals(url, SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)) {
            customerLoginMapper.resetErrorCounts(Wrappers.<CustomerLogin>lambdaQuery()
                .and(obj1 -> obj1.eq(CustomerLogin::getUsername, userId)));
        } else if (StringUtils.equals(url, SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE)) {
            customerLoginMapper.resetErrorCounts(Wrappers.<CustomerLogin>lambdaQuery()
                .and(obj2 -> obj2.eq(CustomerLogin::getMobilePhone, userId)));
        }
    }

    @Override
    public void recordLoginError(String url, String userId, String ip) {
        log.warn(">>>>> 用户:{},IP:{}登录失败", userId, ip);

        if (StringUtils.equals(url, SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)) {
            customerLoginMapper.incErrorCounts(Wrappers.<CustomerLogin>lambdaQuery()
                .and(obj1 -> obj1.eq(CustomerLogin::getUsername, userId)));
        } else if (StringUtils.equals(url, SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE)) {
            customerLoginMapper.incErrorCounts(Wrappers.<CustomerLogin>lambdaQuery()
                .and(obj2 -> obj2.eq(CustomerLogin::getMobilePhone, userId)));
        }
    }
}
