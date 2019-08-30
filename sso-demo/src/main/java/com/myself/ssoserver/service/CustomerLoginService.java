package com.myself.ssoserver.service;

import com.myself.ssoserver.support.ApiResponse;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author Created by zion
 * @Date 2019/8/28.
 */
public interface CustomerLoginService {

    /**
     * 检测当前手机号的使用情况,一个手机号在系统里面只能关联一个账户
     *
     * @param mobilePhone 手机号
     * @return boolean
     */
    ApiResponse checkMobile(String mobilePhone);

    /**
     * 根据输入的手机号生成发送短信的验证码
     *
     * @param mobilePhone 手机号
     * @return 图片或者错误信息
     */
    ApiResponse sendMsgCode(ServletWebRequest request, String mobilePhone);


}
