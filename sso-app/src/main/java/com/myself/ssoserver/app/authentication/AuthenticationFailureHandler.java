package com.myself.ssoserver.app.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myself.ssoserver.app.service.CustomerLoginLogService;
import com.myself.ssoserver.properties.SecurityConstants;
import com.myself.ssoserver.support.ApiResponse;
import com.myself.ssoserver.support.StatusEnum;
import com.myself.ssoserver.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 *
 * @author Created by zion
 * @Date 2019/2/19.
 */
@Slf4j
@Component("authenticationFailureHandler")
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerLoginLogService customerLoginLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        if (StringUtils.equals(request.getRequestURI(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)) {
            String username = request.getParameter("username");
            if (StringUtils.isNotBlank(username)) {
                customerLoginLogService.recordLoginError(request.getRequestURI(), username, IpUtil.getIpAddr(request));
            }
        } else if (StringUtils.equals(request.getRequestURI(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE)) {
            String mobile = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
            if (StringUtils.isNotBlank(mobile)) {
                customerLoginLogService.recordLoginError(request.getRequestURI(), mobile, IpUtil.getIpAddr(request));
            }
        }

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.ofMessage(StatusEnum.UN_AUTHORIZED.getCode(), e.getMessage())));
    }
}
