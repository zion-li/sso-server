package com.myself.ssoserver.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.myself.ssoserver.util.CalendarUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 用户登录表
 *
 * @author Created by zion
 * @Date 2019/8/12.
 */
@Data
public class CustomerLogin extends Model<CustomerLogin> implements UserDetails {

    private static final long serialVersionUID = 1815202622026010487L;
    /**
     * 用户ID
     */
    @TableId
    private Integer customerId;
    /**
     * 用户登陆名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private transient String password;
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

    /**
     * 失败次数
     */
    private Integer errorsCounts;

    /**
     * 权限
     */
    private final Set<GrantedAuthority> authorities;

    public CustomerLogin(Integer customerId, String username, String mobilePhone, String nickname, String password, String icon, Integer userStats, Date modifiedTime, Integer errorsCounts, Collection<? extends GrantedAuthority> authorities) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                "Cannot pass null or empty values to constructor");
        }
        this.customerId = customerId;
        this.username = username;
        this.mobilePhone = mobilePhone;
        this.nickname = nickname;
        this.password = password;
        this.icon = icon;
        this.userStats = userStats;
        this.modifiedTime = modifiedTime;
        this.errorsCounts = errorsCounts;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 当前用户时候过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户状态不是1，代表锁定了，不让登录
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.userStats == 1;
    }

    /**
     * 密码永不过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 今天失败5次了就不让登录了
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        if (this.errorsCounts > 5 && CalendarUtil.isToday(this.modifiedTime)) {
            return false;
        }
        return true;
    }


    private static SortedSet<GrantedAuthority> sortAuthorities(
        Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
            new CustomerLogin.AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
        Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs instanceof CustomerLogin && username.equals(((CustomerLogin) rhs).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.isEnabled()).append("; ");
        sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired())
            .append("; ");
        sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");
        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }
        return sb.toString();
    }
}
