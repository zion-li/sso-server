package com.myself.ssoserver.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Created by zion
 * @Date 2019/8/29.
 */
@Data
public class SendMsgCodeForm {

    /**
     * 手机号
     */
    @NotNull
    private String mobilePhone;

    /**
     * 图片验证码
     */
    @NotNull
    private String imageCode;
}
