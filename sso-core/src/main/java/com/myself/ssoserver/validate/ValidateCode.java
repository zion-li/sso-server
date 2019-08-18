package com.myself.ssoserver.validate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础信息的提取
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
@Builder
@Data
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 8458721201990601418L;

    /**
     * 验证码
     */
    private String code;

    /**
     * 过期时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;

    public ValidateCode() {
    }

    public ValidateCode(String code, int expireInt) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 判断当前的验证码是否过期
     *
     * @return Boolean
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}