package com.fast.dev.frame.database.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明：可以设置默认值的属性列
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:34
 * <p/>
 * 版本：verson 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    public String column() default "";
    public String defaultValue() default "";
}
