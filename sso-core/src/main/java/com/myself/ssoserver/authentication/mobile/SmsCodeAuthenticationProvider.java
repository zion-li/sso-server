package com.myself.ssoserver.authentication.mobile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * 短信登录验证逻辑
 * 由于短信验证码的验证在过滤器里已完成,这里直接读取用户信息即可
 *
 * @author Created by zion
 * @Date 2019/1/30.
 */
@Data
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 用户信息
     */
    private UserDetailsService userDetailsService;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new SmsCodeAuthenticationProvider.SmsCodePreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new SmsCodeAuthenticationProvider.SmsCodePostAuthenticationChecks();


    /**
     * 进行身份认证的逻辑
     * 用UserDetailsService获取用户信息，那到这个用户信息从新组装一个新的
     *
     * @param authentication SmsCodeAuthenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.isInstanceOf(SmsCodeAuthenticationToken.class, authentication,
            () -> messages.getMessage(
                "SmsCodeAuthenticationProvider.onlySupports",
                "Only SmsCodeAuthenticationProvider is supported"));

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法通过手机号获取用户信息");
        }
        preAuthenticationChecks.check(user);
        postAuthenticationChecks.check(user);

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private class SmsCodePreAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("User account is locked");

                throw new LockedException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.locked",
                    "User account is locked"));
            }

            if (!user.isEnabled()) {
                log.debug("User account is disabled");

                throw new DisabledException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.disabled",
                    "User is disabled"));
            }

            if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");

                throw new AccountExpiredException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.expired",
                    "User account has expired"));
            }
        }
    }

    private class SmsCodePostAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");

                throw new CredentialsExpiredException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.credentialsExpired",
                    "User credentials have expired"));
            }
        }
    }
}

