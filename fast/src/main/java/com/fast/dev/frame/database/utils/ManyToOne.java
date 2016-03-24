package com.fast.dev.frame.database.utils;

/**
 * 说明：多对一的字段
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:51
 * <p/>
 * 版本：verson 1.0
 */
public class ManyToOne extends Property {

    private Class<?> manyClass;

    public Class<?> getManyClass() {
        return manyClass;
    }

    public void setManyClass(Class<?> manyClass) {
        this.manyClass = manyClass;
    }

}
