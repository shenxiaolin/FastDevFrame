package com.fast.dev.frame.utils;

/**
 * 说明：UI工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:33
 * <p/>
 * 版本：verson 1.1
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fast.dev.frame.AbstractApplication;

import java.lang.reflect.Field;

public class UIUtils {

    private static Context mContext = null;

    /**
     * 说明：禁止实例化
     */
    private UIUtils(){}

    private static void check(){
        if (mContext == null){
            mContext = AbstractApplication.getContext();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        check();
        Resources r = mContext.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        check();
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(float pxValue) {
        check();
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    public static int sp2px(float spValue) {
        check();
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取资源
     * @return
     */
    private static Resources getResource() {
        return mContext.getResources();
    }

    /**
     * 返回String数组
     * @param id  资源id
     * @return
     */
    public static String[] getStringArray(int id) {
        check();
        return getResource().getStringArray(id);
    }

    /**
     * 说明：获取字符串资源
     * @param id
     * @return
     */
    public static String getString(int id) {
        check();
        return getResource().getString(id);
    }
    /**
     * 说明：获取颜色资源
     * @param id
     * @return
     */
    public static int getColor(int id) {
        check();
        return getResource().getColor(id);
    }
    /**
     * 说明：获取屏幕的宽度
     * @return
     */
    public static int screenWidth(){
        check();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    /**
     * 说明：获取屏幕的高度
     * @return
     */
    public static int screenHeight(){
        check();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    /**
     * 说明：获取屏幕的密度
     * @return
     */
    public static float density(){
        check();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * 说明：获取状态栏的高度
     * @return
     */
    public static int getStatusBarHeight(){
        check();
        int height = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int x = NumberUtils.toInt(field.get(object).toString());
            height = getResource().getDimensionPixelSize(x);
        }catch (Exception e){
            e.printStackTrace();
        }
        return height;
    }

    /**
     * 说明：获取布局文件
     * @param id
     * @param group
     * @param flag
     * @return
     */
    public static View inflate(int id,ViewGroup group,boolean flag){
        check();
        return LayoutInflater.from(mContext).inflate(id,group,flag);
    }
    /**
     * 说明：获取布局文件
     * @param id
     * @return
     */
    public static View inflate(int id){
        check();
        return LayoutInflater.from(mContext).inflate(id,null,false);
    }

//    /**
//     * 说明：获取控件高度
//     * @param view
//     * @return
//     */
//    public static int getViewHeight(View view){
//        viewview.getViewTreeObserver()
//    }
}


