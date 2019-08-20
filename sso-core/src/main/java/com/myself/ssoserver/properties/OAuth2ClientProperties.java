package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * @author Created by zion
 * @Date 2019/2/20.
 */
@Data
public class OAuth2ClientProperties {

    /**
     * 第三方应用appId
     */
    private String clientId;

    /**
     * 第三方应用appSecret
     */
    private String clientSecret;

    /**
     * 重定向的URL
     */
    private String redirectUris;

    /**
     * 针对此应用发出的token的有效时间(2小时),如果“0”就永不过期
     */
    private int accessTokenValiditySeconds = 7200;

    /**
     * 针对此应用发出的refresh token的有效时间(2周)
     */
    private int refreshTokenValiditySeconds = 1209600;
}
