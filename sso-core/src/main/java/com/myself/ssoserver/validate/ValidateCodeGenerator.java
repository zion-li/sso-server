package com.myself.ssoserver.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
public interface ValidateCodeGenerator {

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
