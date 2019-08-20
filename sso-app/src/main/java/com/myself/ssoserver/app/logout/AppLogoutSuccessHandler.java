package com.myself.ssoserver.app.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myself.ssoserver.app.AppSecretException;
import com.myself.ssoserver.support.ApiResponse;
import com.myself.ssoserver.support.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Created by zion
 * @Date 2019/8/18.
 */
@Slf4j
@Component
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        String refreshToken = request.getHeader("refresh_token");
        if (refreshToken == null) {
            throw new AppSecretException("退出失败");
        }

        if (tokenStore instanceof RedisTokenStore) {
            OAuth2RefreshToken oAuth2AccessToken = tokenStore.readRefreshToken(refreshToken);
            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessTokenUsingRefreshToken(oAuth2AccessToken);
                tokenStore.removeRefreshToken(oAuth2AccessToken);
            }
        }

        log.info("退出成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.ofMessage(StatusEnum.SUCCESS.getCode(), "退出成功")));
    }
}
