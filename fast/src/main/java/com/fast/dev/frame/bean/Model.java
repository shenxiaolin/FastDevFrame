package com.fast.dev.frame.bean;

import com.fast.dev.frame.utils.GsonUtils;

/**
 * 说明：Bean继承该类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/25 20:45
 * <p/>
 * 版本：verson 1.0
 */
public class Model implements I_Model{
    @Override
    public String getType() {
        return getClass().getName();
    }

    @Override
    public <T> T toBean(String json) {
        return GsonUtils.toBean(json, getType());
    }

    @Override
    public <T> T toList(String json) {
        return GsonUtils.toList(json,getType());
    }
}
