package com.fast.dev.frame.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明：设置View
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/4 10:52
 * <p/>
 * 版本：verson 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    int value();
}
