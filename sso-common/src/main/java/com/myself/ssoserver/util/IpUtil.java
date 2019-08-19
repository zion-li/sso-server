package com.myself.ssoserver.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Created by zion
 * @Date 2019/8/19.
 */
public class IpUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
