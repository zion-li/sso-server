package com.myself.ssoserver.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 所有的系统属性的总的出口
 *
 * @author Created by zion
 * @Date 2019/1/28
 */
@Data
@ConfigurationProperties(prefix = "security-properties")
public class SecurityProperties {
    /**
     * 浏览器相关属性
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 认证相关属性
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 第三方登录相关属性
     */
    private SocialProperties social = new SocialProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();
}
