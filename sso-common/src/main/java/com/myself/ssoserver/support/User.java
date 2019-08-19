package com.myself.ssoserver.support;

import lombok.*;

/**
 * 用户表，根据实际的进行更改
 *
 * @author Created by zion
 * @Date 2019/5/7.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class User {
    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 登录名称
     */
    private Integer mobilePhone;

    /**
     * 用户名称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 状态
     */
    private Integer userStats;

}
