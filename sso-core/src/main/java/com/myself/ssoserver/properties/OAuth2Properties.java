package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * @author Created by zion
 * @Date 2019/2/20.
 */
@Data
public class OAuth2Properties {

    /**
     * 使用jwt时为token签名的秘钥
     */
    private String jwtSigningKey = "Aa123hTr3etBe7rdf8f$%&aSFaDS+Fs";

    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};
}
