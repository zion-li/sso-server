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
     *
     * @param url    访问URL
     * @param userId 用户标识（username or mobilePhone）
     * @param ip     IP地址
     */
    void recordLoginSuccess(String url, String userId, String ip);

    /**
     * 记录失败登录登录操作信息
     *
     * @param url    访问URL
     * @param userId 用户标识（username or mobilePhone）
     * @param ip     IP地址
     */
    void recordLoginError(String url, String userId, String ip);
}
