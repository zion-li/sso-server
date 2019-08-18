package com.myself.ssoserver.validate.image;


import com.myself.ssoserver.validate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
@Data
public class ImageValidateCode extends ValidateCode {

    public BufferedImage image;

    public ImageValidateCode(String code, int expireInt, BufferedImage image) {
        super(code, expireInt);
        this.image = image;
    }

    public ImageValidateCode(String code, LocalDateTime expireTime, BufferedImage image) {
        super(code, expireTime);
        this.image = image;
    }
}
