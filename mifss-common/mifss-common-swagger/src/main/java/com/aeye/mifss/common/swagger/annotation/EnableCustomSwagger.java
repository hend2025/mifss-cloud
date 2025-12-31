package com.aeye.mifss.common.swagger.annotation;

import com.aeye.mifss.common.swagger.config.SwaggerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Swagger注解
 * 在启动类上添加此注解即可开启Swagger
 *
 * @author mifss
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ SwaggerConfig.class })
public @interface EnableCustomSwagger {
}
