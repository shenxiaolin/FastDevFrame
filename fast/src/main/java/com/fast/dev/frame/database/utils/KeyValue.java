package com.fast.dev.frame.database.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 说明：键值对封装
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:51
 * <p/>
 * 版本：verson 1.0
 */
public class KeyValue {
    private String key;
    private Object value;

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public Object getValue() {
        if (value instanceof java.util.Date) {
            return sdf.format(value);
        }
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
