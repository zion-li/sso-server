package com.myself.ssoserver.properties;

/**
 * 所有的属性配置(SecurityProperties 包含下面的4个基础配置)
 * browserProperties;validateCodeProperties;oauth2Properties;socialProperties
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
public interface SecurityConstants {

    /**
     * browserProperties----当请求需要身份认证时，默认跳转的url
     */
    String APP_DEFAULT_UNAUTHENTICATION_URL = "/login";
    /**
     * browserProperties----当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * browserProperties----默认登录页面
     */
    String DEFAULT_SIGN_IN_PAGE_URL = "/browser-signIn.html";

    /**
     * ValidateCodeProperties----验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * ValidateCodeProperties----验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * ValidateCodeProperties----默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/authentication/openid";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_USERNAME = "username";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/session/invalid";

    /**
     * openid参数名
     */
    String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * providerId参数名
     */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

    /**
     * remember me的redis的特殊前缀key
     */
    String DEFAULT_REMEMBER_ME_KEY = "BFD_REMEMBER_ME:";


    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
}
