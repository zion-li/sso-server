package com.myself.ssoserver.support;

/**
 * @author Created by zion
 * @Date 2018/8/23.
 */
public enum StatusEnum {

    SUCCESS(200, "请求成功"),
    BAD_REQUEST(400, "请求错误"),
    UN_AUTHORIZED(401, "用户身份验证失败"),
    FORBIDDEN(403, "禁止重复提交"),
    NOT_FOUND(404, "请求连接错误"),
    Method_Not_Allowed(405, "客户端请求中的方法被禁止"),
    NOT_ACCEPTABLE(406, "服务器无法根据客户端请求的内容特性完成请求"),
    REQUEST_TIMED_OUT(408, "请求超时"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误，无法完成请求"),
    NOT_VALID_PARAM(40001, "参数异常"),
    NOT_SUPPORTED_OPERATION(40002, "请求禁止"),
    SESSION_INVALIDATION(40003, "session失效"),
    BINDING_SUCCESS(40004, "绑定成功"),
    UNTIED_SUCCESS(40005, "解绑成功"),
    NOT_LOGIN(50000, "没有登录");

    private int code;
    private String standardMessage;

    StatusEnum(int code, String standardMessage) {
        this.code = code;
        this.standardMessage = standardMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStandardMessage() {
        return standardMessage;
    }

    public void setStandardMessage(String standardMessage) {
        this.standardMessage = standardMessage;
    }
}
