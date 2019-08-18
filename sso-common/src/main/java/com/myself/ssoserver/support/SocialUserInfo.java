package com.myself.ssoserver.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 做第三方登录的时候需要将第三方的一个账户绑定到现有系统的一个账户
 * 此时提供的地上第三方的信息
 *
 * @author Created by zion
 * @Date 2019/2/14.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserInfo {

    /**
     * 那个第三方做社交登录
     */
    private String providerId;

    /**
     * openId
     */
    private String providerUserId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headimg;
}
