package com.myself.ssoserver.validate.sms;

/**
 * 抽象出短信验证码发送功能
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
public interface SmsValidateCodeSender {
    /**
     * 给某个手机号发送哪个验证码
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
