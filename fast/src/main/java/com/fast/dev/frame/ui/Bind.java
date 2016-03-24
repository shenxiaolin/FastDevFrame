package com.fast.dev.frame.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明：注解式绑定注解
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:38
 * <p/>
 * 版本：verson 1.0
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
    int id();
    boolean click() default false;
}

