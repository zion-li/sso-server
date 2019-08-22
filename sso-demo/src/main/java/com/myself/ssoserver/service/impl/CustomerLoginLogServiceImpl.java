package com.myself.ssoserver.service.impl;

import com.myself.ssoserver.app.service.CustomerLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Created by zion
 * @Date 2019/8/21.
 */
@Slf4j
@Service
public class CustomerLoginLogServiceImpl implements CustomerLoginLogService {

    @Override
    public void recordLoginSuccess(String url, String username, String ip) {
        log.error("用户:{},IP:{}登录成功", username, ip);
    }

    @Override
    public void recordLoginerror(String url, String username, String ip) {
        log.error("用户:{},IP:{}登录失败", username, ip);
    }
}
