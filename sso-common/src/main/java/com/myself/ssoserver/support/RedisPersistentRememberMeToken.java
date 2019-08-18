package com.myself.ssoserver.support;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by zion
 * @Date 2019/3/26.
 */
@Data
public class RedisPersistentRememberMeToken implements Serializable {

    private static final long serialVersionUID = -5183256984063585990L;

    private String username;
    private String series;
    private String tokenValue;
    private Date date;

    public RedisPersistentRememberMeToken() {
    }

    public RedisPersistentRememberMeToken(String username, String series, String tokenValue, Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }
}
