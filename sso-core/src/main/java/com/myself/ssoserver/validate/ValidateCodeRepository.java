package com.myself.ssoserver.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 *
 * @author Created by zion
 * @Date 2019/2/16.
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param request request
     * @param key     key
     * @param code    ValidateCode序列化对象
     */
    void save(ServletWebRequest request, String key, ValidateCode code);

    /**
     * 获取验证码
     *
     * @param request request
     * @param key     key
     * @return ValidateCode序列化对象
     */
    ValidateCode get(ServletWebRequest request, String key);

    /**
     * 移除验证码
     *
     * @param request request
     * @param key     key
     */
    void remove(ServletWebRequest request, String key);
}
