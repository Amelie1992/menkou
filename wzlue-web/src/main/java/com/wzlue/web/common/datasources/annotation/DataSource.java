package com.wzlue.web.common.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author chenshun
 * @email wzlue.com
 * @date 2017/9/16 22:16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
