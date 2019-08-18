package com.myself.ssoserver.validate;


import com.myself.ssoserver.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端请求验证码 controller
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * 发送验证码
     *
     * @param request  请求
     * @param response 响应
     * @param type     请求类型，支持(image,sms)
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String type) throws Exception {

        validateCodeProcessorHolder.findValidateCodeProcessor(type)
            .create(new ServletWebRequest(request, response));
    }

}
