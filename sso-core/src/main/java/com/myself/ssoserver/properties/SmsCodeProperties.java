package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * 短信相关属性
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
@Data
public class SmsCodeProperties {
    /**
     * 验证码的长度,默认6个数字
     */
    private int length = 6;
    /**
     * 验证码的失效时间，默认2分钟
     */
    private int expireIn = 120;

    /**
     * 要拦截的路径
     */
    private String url;
}
