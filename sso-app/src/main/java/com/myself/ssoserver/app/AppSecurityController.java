package com.myself.ssoserver.app;


import com.myself.ssoserver.properties.SecurityConstants;
import com.myself.ssoserver.support.ApiResponse;
import com.myself.ssoserver.support.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户使用第三方登录的时候如果在本系统需要注册的化，
 * 需要给前端页面显示一些地方放的信息的接口
 *
 * @author Created by zion
 * @Date 2019/2/20.
 */
@RestController
public class AppSecurityController {

    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        return ApiResponse.ofStatus(StatusEnum.NOT_LOGIN);
    }
}
