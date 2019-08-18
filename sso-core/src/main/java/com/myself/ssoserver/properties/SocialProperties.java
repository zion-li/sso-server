package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * @author Created by zion
 * @Date 2019/1/28.
 */
@Data
public class SocialProperties {

    /**
     * social拦截的路径，这块支持个性化配置了，如果不配置，那么依旧使用默认配置
     */
    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeChatProperties weChat = new WeChatProperties();
}
