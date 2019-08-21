package com.myself.ssoserver.security;

import com.myself.ssoserver.model.CustomerLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 用户对象
 *
 * @author zhailiang
 */
@Slf4j
@Component
public class DemoUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        log.info("普通表单登录用户名:" + loginName);
        return buildUser(loginName);
    }

    /**
     * 根据用户名查找用户信息，这块必须把密码返回，DaoAuthenticationProvider会比较密码
     *
     * @param userId
     * @return
     */
    private UserDetails buildUser(String userId) {
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码是:" + password);
        return new CustomerLogin(1, "root", "18102466330", "你大爷", password, null,
            1, new Date(), 0, AuthorityUtils.commaSeparatedStringToAuthorityList("xxx"));
    }

}
