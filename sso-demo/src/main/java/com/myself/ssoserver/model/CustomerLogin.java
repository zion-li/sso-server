package com.myself.ssoserver.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * 用户登录表
 *
 * @author Created by zion
 * @Date 2019/8/12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerLogin extends Model<CustomerLogin> implements UserDetails{

    private static final long serialVersionUID = 1815202622026010487L;
    /**
     * 用户ID
     */
    @TableId
    private Integer customerId;
    /**
     * 用户登陆名
     */
    private String loginName;
    /**
     * 手机号
     */
    private Integer mobilePhone;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String icon;
    /**
     * 用户状态：1 正常, 2 锁定, 3 注销
     */
    private Integer userStats;
    /**
     * 最后修改时间
     */
    private Date modifiedTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
