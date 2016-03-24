package com.fast.dev.frame.database.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明：检测 字段是否已经被标注为 非数据库字段
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:42
 * <p/>
 * 版本：verson 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {

}
