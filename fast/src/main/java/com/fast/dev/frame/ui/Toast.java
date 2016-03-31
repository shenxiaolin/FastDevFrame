package com.fast.dev.frame.ui;

import android.support.design.widget.Snackbar;

import com.fast.dev.frame.FastFrame;
import com.fast.dev.frame.utils.UIUtils;


/**
 * 说明：View提示的工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015-8-26
 * <p/>
 * 版本：verson 1.0
 */

public class Toast {

    private static Toast inject;
    private android.widget.Toast mToast;
    private Snackbar mSnackbar;

    /*禁止实例化*/
    private Toast() {
    }

    public static Toast get() {
        if (inject == null) {
            synchronized (Toast.class) {
                if (inject == null) {
                    inject = new Toast();
                }
            }
        }
        return inject;
    }

    /**
     * 说明：显示短Toast
     *
     * @param msg
     */
    public android.widget.Toast shortToast(String msg) {
        return showToast(msg, android.widget.Toast.LENGTH_SHORT);
    }


    /**
     * 说明：显示短Toast
     *
     * @param msg
     */
    public android.widget.Toast shortToast(int msg) {
        return shortToast(UIUtils.getString(msg));
    }

    /**
     * 说明：显示Toast
     * @param msg
     * @param time
     * @return
     */
    private android.widget.Toast showToast(String msg, int time) {
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(FastFrame.getApplication(), msg, time);
        } else {
            mToast.setText(msg);
            mToast.setDuration(time);
        }
        mToast.show();
        return mToast;
    }

    /**
     * 说明：取消显示Toast
     */
    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 说明：显示长Toast
     *
     * @param msg
     */
    public android.widget.Toast longToast(String msg) {
        return showToast(msg, android.widget.Toast.LENGTH_LONG);
    }

    /**
     * 说明：显示长Toast
     *
     * @param msg
     */
    public android.widget.Toast longToast(int msg) {
        return longToast(UIUtils.getString(msg));
    }

}

