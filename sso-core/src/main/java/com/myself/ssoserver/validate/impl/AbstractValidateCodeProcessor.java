package com.myself.ssoserver.validate.impl;

import com.myself.ssoserver.properties.SecurityConstants;
import com.myself.ssoserver.validate.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 模板方法模式
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository sessionStrategy;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }


    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    @Override
    public ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(this.getClass().getSimpleName(), "ValidateCodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 将当前的校验码保存到session里面
     *
     * @param request      请求信息
     * @param validateCode 验证码对象
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        sessionStrategy.save(request, getSessionKey(), code);
    }

    private String getSessionKey() {
        return SecurityConstants.SESSION_KEY_PREFIX + getValidateCodeType().toString().toUpperCase();
    }

    /**
     * 抽象类：发送校验码，由子类实现
     *
     * @param request      请求信息
     * @param validateCode 验证码对象
     * @throws Exception 验证码发送失败异常
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request, String codeInRequest) {

        ValidateCodeType processorType = getValidateCodeType();
        String sessionKey = getSessionKey();
        C codeInSession = (C) sessionStrategy.get(request, sessionKey);

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.remove(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.remove(request, sessionKey);
    }

}
