package com.myself.ssoserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 演示项目启动类
 *
 * @author Created by zion
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.myself"})
@MapperScan("com.myself.ssoserver.mapper")
public class SsoSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoSecurityApplication.class, args);
    }

}

