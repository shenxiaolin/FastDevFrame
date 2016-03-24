package com.fast.dev.frame.database.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明：Id主键配置,不配置的时候默认找类的id或_id字段作为主键，column不配置的是默认为字段名
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:32
 * <p/>
 * 版本：verson 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
    /**
     * 设置为主键
     *
     * @return
     */
    public String column() default "";
}
