package com.fast.dev.frame.sample;

import android.app.Application;

import com.fast.dev.frame.FastFrame;

/**
 * 说明：
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/24 11:51
 * <p/>
 * 版本：verson 1.0
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FastFrame.init(this);
    }
}
