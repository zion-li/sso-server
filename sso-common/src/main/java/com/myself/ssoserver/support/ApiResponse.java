package com.myself.ssoserver.support;


import lombok.Data;
import lombok.ToString;

/**
 * 返回的json数据格式
 *
 * @author Created by zion
 * @Date 2018/8/17.
 */
@Data
@ToString
public class ApiResponse {
    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 额外信息
     */
    private boolean more;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
        this.code = StatusEnum.SUCCESS.getCode();
        this.message = StatusEnum.SUCCESS.getStandardMessage();
    }

    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getStandardMessage(), data);
    }

    public static ApiResponse ofStatus(StatusEnum statusEnum) {
        return new ApiResponse(statusEnum.getCode(), statusEnum.getStandardMessage(), null);
    }
}
