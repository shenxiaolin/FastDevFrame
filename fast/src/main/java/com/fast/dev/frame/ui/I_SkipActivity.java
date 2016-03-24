package com.fast.dev.frame.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 说明：规范Activity跳转的接口协议
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:50
 * <p/>
 * 版本：verson 1.0
 */
public interface I_SkipActivity {
    void skipActivity(Activity activity, Class<?> cls);
    void skipActivity(Activity activity, Intent intent);
    void skipActivity(Activity activity, Class<?> cls, Bundle bundle);
    void showActivity(Activity activity, Class<?> cls);
    void showActivity(Activity activity, Intent intent);
    void showActivity(Activity activity, Class<?> cls, Bundle bundle);
    void showActivityForResult(Activity activity, Class<?> cls, int requestCode);
}
