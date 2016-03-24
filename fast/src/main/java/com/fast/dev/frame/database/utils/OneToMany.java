package com.fast.dev.frame.database.utils;

/**
 * 说明：一对多的字段
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:52
 * <p/>
 * 版本：verson 1.0
 */
public class OneToMany extends Property {

    private Class<?> oneClass;

    public Class<?> getOneClass() {
        return oneClass;
    }

    public void setOneClass(Class<?> oneClass) {
        this.oneClass = oneClass;
    }

}
