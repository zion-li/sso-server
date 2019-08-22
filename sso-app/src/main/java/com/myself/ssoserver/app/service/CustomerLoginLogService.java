package com.myself.ssoserver.app.service;

/**
 * 用户登录服务
 *
 * @author Created by zion
 * @Date 2019/8/21.
 */
public interface CustomerLoginLogService {

    /**
     * 记录成功登录操作信息
     */
    void recordLoginSuccess(String url, String username, String ip);

    /**
     * 记录失败登录登录操作信息
     */
    void recordLoginerror(String url, String username, String ip);
}
