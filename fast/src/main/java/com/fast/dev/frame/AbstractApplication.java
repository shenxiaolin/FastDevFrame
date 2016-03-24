package com.fast.dev.frame;

import android.app.Application;

/**
 * 说明：AbstractApplication
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 10:34
 * <p/>
 * 版本：verson 1.0
 */
public class AbstractApplication extends Application{

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * 说明：获取上下文
     * @return
     */
    public static Application getContext(){
        return mApplication;
    }
}
