package com.fast.dev.frame.database.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明：表名注解
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:35
 * <p/>
 * 版本：verson 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 表名
     */
    public String name();
}
