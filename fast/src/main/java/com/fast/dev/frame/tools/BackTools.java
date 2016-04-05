package com.fast.dev.frame.tools;

import android.app.Activity;
import android.view.KeyEvent;

import com.fast.dev.frame.interfaces.BackExit;

/**
 * 说明：双击退出应用
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/5 16:32
 * <p/>
 * 版本：verson 1.0
 */
public class BackTools {

    private static long touchTime = 0;

    public static void onBackPressed(BackExit backExit){
        long waitTime = backExit.setWaitTime();
        if (waitTime <= 0){
            waitTime = 2000;
        }
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime){
            backExit.showTips();
            touchTime = currentTime;
        }else {
            //退出
            backExit.exit();
        }
    }

}
