package com.yy.live.aoptraining.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: YPermission
 * @classDescription: 动态权限申请注解
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YPermission {
    String value();
}
