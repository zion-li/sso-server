package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * @author Created by zion
 * @Date 2019/8/14.
 */
@Data
public class SocialCommonProperties {
    /**
     * Application id
     */
    private String appId;
    /**
     * Application secret
     */
    private String appSecret;

    /**
     * 第三方id，用来决定发起第三方登录的url。
     */
    private String providerId;
}
