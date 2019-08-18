package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * QQ登录配置项
 *
 * @author Created by zion
 * @Date 2019/2/13.
 */
@Data
public class QQProperties extends SocialCommonProperties {

    QQProperties() {
        setProviderId("qq");
    }
}
