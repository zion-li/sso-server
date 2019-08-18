package com.myself.ssoserver.app;

/**
 * 自定义的一个异常类
 * @author Created by zion
 * @Date 2019/2/20.
 */
public class AppSecretException extends RuntimeException {

    public AppSecretException(String msg) {
        super(msg);
    }
}
