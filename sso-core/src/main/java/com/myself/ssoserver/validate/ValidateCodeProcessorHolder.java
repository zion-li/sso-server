package com.myself.ssoserver.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 校验码处理器管理器,通过输入的类型获取到不同的image/sms的processor
 *
 * @author Created by zion
 * @Date 2019/1/28.
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 根据输入的类型获取image或者sms的实现类，参数是枚举
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString());
    }

    /**
     * 根据输入的类型获取image或者sms的实现类，参数是字符串
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器\"" + name + "\"不存在");
        }
        return processor;

    }
}
