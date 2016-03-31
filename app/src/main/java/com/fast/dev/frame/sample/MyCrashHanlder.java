package com.fast.dev.frame.sample;

import com.fast.dev.frame.ui.DefaultCrashHandler;
import com.fast.dev.frame.ui.Toast;

/**
 * 说明：MyCrashHanlder
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/30 20:28
 * <p/>
 * 版本：verson 1.0
 */
public class MyCrashHanlder extends DefaultCrashHandler{

    private final static MyCrashHanlder crashHanlder = new MyCrashHanlder();

    public static MyCrashHanlder getInstance(){
        return crashHanlder;
    }

    @Override
    public void showCrashTip() {
        Toast.get().shortToast("程序崩溃了");
    }

}
