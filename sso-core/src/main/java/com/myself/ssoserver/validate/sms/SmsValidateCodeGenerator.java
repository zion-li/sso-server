package com.myself.ssoserver.validate.sms;


import com.myself.ssoserver.properties.SecurityProperties;
import com.myself.ssoserver.validate.ValidateCode;
import com.myself.ssoserver.validate.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成短信验证码
 * 直接注入了，这个简单不需要再自定义实现了，就生成数字
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
@Component("smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }
}
