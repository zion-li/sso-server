package com.myself.ssoserver.validate.image;


import com.myself.ssoserver.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 子类实现的发送验证码的功能
 *
 * @author Created by zion
 * @Date 2019/1/29.
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageValidateCode> {

    @Override
    protected void send(ServletWebRequest request, ImageValidateCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
