package com.fast.dev.frame;

import android.app.Application;

/**
 * 说明：初始化
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/25 20:39
 * <p/>
 * 版本：verson 1.0
 */
public class FastFrame {

    private static Application mApplication;

    public static void init(Application application){
        mApplication = application;
    }

    /**
     * 说明：获取上下文
     * @return
     */
    public static Application getContext(){
        return mApplication;
    }

}
