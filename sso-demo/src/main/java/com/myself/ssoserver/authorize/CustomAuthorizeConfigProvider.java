package com.myself.ssoserver.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 自定义权限拦截器
 *
 * @author Created by zion
 * @Date 2019/8/28.
 */
@Component
@Order(Integer.MAX_VALUE)
public class CustomAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
            .antMatchers(HttpMethod.POST, "/register").permitAll()
            .antMatchers("/customer/*").permitAll();
        return true;
    }
}