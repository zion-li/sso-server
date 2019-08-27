package com.myself.ssoserver.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myself.ssoserver.mapper.CustomerLoginMapper;
import com.myself.ssoserver.model.CustomerLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


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

    @Autowired
    private CustomerLoginMapper customerLoginMapper;


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
        QueryWrapper<CustomerLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper
            .select("customer_id", "username", "mobile_phone", "nickname","password")
            .lambda()
            .or(obj1 -> obj1.eq(CustomerLogin::getUsername, userId))
            .or(obj2 -> obj2.eq(CustomerLogin::getMobilePhone, userId));
        return customerLoginMapper.selectOne(queryWrapper);
    }
}
