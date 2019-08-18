package com.myself.ssoserver;

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
public class SsoSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoSecurityApplication.class, args);
    }

}

