package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * @author Created by zion
 * @Date 2019/1/28.
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
