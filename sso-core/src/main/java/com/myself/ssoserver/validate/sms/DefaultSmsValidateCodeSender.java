package com.myself.ssoserver.validate.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信验证码发送器
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
@Slf4j
public class DefaultSmsValidateCodeSender implements SmsValidateCodeSender {
    @Override
    public void send(String mobile, String code) {
        log.error("需要您实现com.bfd.validate.sms.SmsValidateCodeSender,重写手机验证码发送逻辑");
        log.error("向手机号{}发送验证码 {} 功能", mobile, code);
    }
}
