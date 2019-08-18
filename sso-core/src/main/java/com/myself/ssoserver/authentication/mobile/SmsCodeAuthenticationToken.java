package com.myself.ssoserver.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 仿照UsernamePasswordAuthenticationFilter封装出当前的这个短信的token认证信息
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 8374341200848795658L;

    /**
     * 认证信息，认证之前手机号，认证之后放登录成功的用户信息
     */
    private final Object principal;

    /**
     * 没有登录的时候
     *
     * @param mobile 手机号(没有认证之前)
     */
    public SmsCodeAuthenticationToken(String mobile) {
        super((Collection) null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    /**
     * 登录成功
     *
     * @param mobile      不是手机号了，是完整的用户信息
     * @param authorities 权限
     */
    public SmsCodeAuthenticationToken(Object mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = mobile;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}