package com.myself.ssoserver.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常类
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
