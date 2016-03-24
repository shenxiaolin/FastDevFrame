package com.fast.dev.frame.ui;

/**
 * 说明：Activity基类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/29 0:19
 * <p/>
 * 版本：verson 1.0
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fast.dev.frame.utils.LogUtils;


public abstract class AbstractActivity extends FrameActivity{

    @Override
    protected void onCreate(Bundle bundle) {
        LogUtils.v(this.getClass().getName(), "-->onCreate");
        ActivityStack.create().addActivity(this);
        super.onCreate(bundle);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.create().finishActivity(this);
    }

    @Override
    public void skipActivity(Activity activity, Class<?> cls) {
        showActivity(activity, cls);
        finish();
    }

    @Override
    public void skipActivity(Activity activity, Intent intent) {
        showActivity(activity, intent);
        finish();
    }

    @Override
    public void skipActivity(Activity activity, Class<?> cls, Bundle bundle) {
        showActivity(activity, cls, bundle);
        finish();
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivityForResult(Activity activity, Class<?> cls,int requestCode) {
        Intent intent = new Intent(activity,cls);
        activity.startActivityForResult(intent,requestCode);
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity,cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}

