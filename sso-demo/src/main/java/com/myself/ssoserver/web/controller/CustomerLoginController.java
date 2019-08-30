package com.myself.ssoserver.web.controller;

import com.myself.ssoserver.service.CustomerLoginService;
import com.myself.ssoserver.support.ApiResponse;
import com.myself.ssoserver.util.RegexUtil;
import com.myself.ssoserver.web.form.SendMsgCodeForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 会员注册管理
 *
 * @author Created by zion
 * @Date 2019/8/28.
 */
@RestController
@RequestMapping("/customer/")
@Api(tags = "CustomerLoginController", description = "会员注册管理")
public class CustomerLoginController {

    @Autowired
    private CustomerLoginService customerLoginService;

    @ApiOperation("新注册手机号使用情况检查")
    @GetMapping(value = "/checkMobile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResponse checkMobile(@RequestParam String mobilePhone) {
        if (!RegexUtil.isMobileExact(mobilePhone)) {
            throw new IllegalArgumentException("手机号不合法");
        }
        return customerLoginService.checkMobile(mobilePhone);
    }

    @ApiOperation("发送短信验证码")
    @PostMapping(value = "/sendMsg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResponse sendMsgCode(ServletWebRequest request, @RequestBody SendMsgCodeForm form) {
        if (!RegexUtil.isMobileExact(form.getMobilePhone())) {
            throw new IllegalArgumentException("手机号不合法");
        }
        return customerLoginService.sendMsgCode(request, form.getMobilePhone());
    }


    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResponse register(@RequestParam String password, @RequestParam String telephone, @RequestParam String authCode) {
        return null;
//        return customerLoginService.register(password, telephone, authCode);
    }
}
