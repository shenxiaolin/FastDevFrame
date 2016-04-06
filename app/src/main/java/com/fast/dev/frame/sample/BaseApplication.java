package com.fast.dev.frame.sample;

import android.app.Application;

import com.fast.dev.frame.FastFrame;
import com.fast.dev.frame.http.HttpConfig;

/**
 * 说明：BaseApplication
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
        //初始化框架
        FastFrame.init(this);
        //异常处理
        MyCrashHanlder.getInstance().init();
        //网络框架设置初始化
        HttpConfig.Builder builder = new HttpConfig.Builder();
        builder.setTrustAll(true);
        builder.build().init();
    }
}
