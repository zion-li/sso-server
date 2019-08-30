package com.myself.ssoserver.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
public interface ValidateCodeProcessor {

    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest, String codeInRequest);

    /**
     * @return
     */
    ValidateCodeType getValidateCodeType();
}
