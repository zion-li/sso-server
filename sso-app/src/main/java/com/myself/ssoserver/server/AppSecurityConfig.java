package com.myself.ssoserver.server;

import com.myself.ssoserver.authentication.FormAuthenticationConfig;
import com.myself.ssoserver.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.myself.ssoserver.authorize.AuthorizeConfigManager;
import com.myself.ssoserver.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * 创建AuthenticationManager，添加自定义登录配置
 *
 * @author Created by zion
 * @Date 2019/2/21.
 */
@Component
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }
}
