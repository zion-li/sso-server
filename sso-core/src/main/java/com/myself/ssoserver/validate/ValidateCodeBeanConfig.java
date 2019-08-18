package com.myself.ssoserver.validate;


import com.myself.ssoserver.properties.SecurityProperties;
import com.myself.ssoserver.validate.image.ImageValidateCodeGenerator;
import com.myself.ssoserver.validate.sms.DefaultSmsValidateCodeSender;
import com.myself.ssoserver.validate.sms.SmsValidateCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，
 * 业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageValidateCodeGenerator codeGenerator = new ImageValidateCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }


    @Bean
    @ConditionalOnMissingBean(SmsValidateCodeSender.class)
    public SmsValidateCodeSender smsValidateCodeSender() {
        return new DefaultSmsValidateCodeSender();
    }
}
