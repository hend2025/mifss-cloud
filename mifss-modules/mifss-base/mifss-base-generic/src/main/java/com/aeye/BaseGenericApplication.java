package com.aeye;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 基础支撑服务 - 开源云版
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.aeye.mifss" })
@MapperScan("com.aeye.mifss.base.mapper")
@EnableDubbo(scanBasePackages = "com.aeye.mifss.base")
public class BaseGenericApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseGenericApplication.class, args);
    }
}
