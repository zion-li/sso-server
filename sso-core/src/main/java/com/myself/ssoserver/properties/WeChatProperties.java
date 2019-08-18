package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * @author Created by zion
 * @Date 2019/2/14.
 */
@Data
public class WeChatProperties extends SocialCommonProperties {

    WeChatProperties() {
        setProviderId("weChat");
    }
}
