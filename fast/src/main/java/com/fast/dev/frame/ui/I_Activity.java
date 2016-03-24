package com.fast.dev.frame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 说明：Activity接口协议
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:40
 * <p/>
 * 版本：verson 1.0
 */
public interface I_Activity {
    /*设置界面*/
    int setRootView();
    /*初始化数据*/
    void onInit(Bundle bundle);
    /*在线程中初始化数据*/
    void onInitThread();
    /*点击事件回调方法*/
    void clickView(View view);
    /*获取数据*/
    void getIntentData(Intent intent);
}
