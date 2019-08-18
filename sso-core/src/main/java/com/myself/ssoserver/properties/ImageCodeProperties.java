package com.myself.ssoserver.properties;

import lombok.Data;

/**
 * 图片验证码配置项
 *
 * @author Created by zion
 * @Date 2019/1/29
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 默认的图片宽度(http请求中可以带这个参数进行更改)
     */
    private int width = 67;
    /**
     * 默认的图片高度(http请求中可以带这个参数进行更改)
     */
    private int height = 23;

    /**
     * 设置图片验证码的位数默认为4
     */
    ImageCodeProperties() {
        setLength(4);
    }
}
