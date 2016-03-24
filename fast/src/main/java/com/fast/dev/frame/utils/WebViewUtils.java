package com.fast.dev.frame.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import com.fast.dev.frame.view.ProgressWebView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 说明：加载webview工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/19 16:33
 * <p/>
 * 版本：verson 1.0
 */
public class WebViewUtils {

    /**
     * 初始化webview
     * @param cordovaWebView webview对象
     * @param context 上下文对象
     * @param url 加载的url路径
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static void webViewInit(ProgressWebView cordovaWebView,Context context,String url){
        webViewInit(cordovaWebView,context,url,true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void webViewInit(ProgressWebView cordovaWebView,Context context,String url,boolean isOpenProgress) {
        WebViewReflect.checkCompatibility();
        cordovaWebView.setInitialScale(100);
        cordovaWebView.setVerticalScrollBarEnabled(false);
        cordovaWebView.requestFocusFromTouch();
        WebSettings settings = cordovaWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);// 设置可以使用localStorage
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = settings.getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(cordovaWebView.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // Enable DOM storage
        WebViewReflect.setDomStorage(settings);

        // Enable built-in geolocation
        WebViewReflect.setGeolocationEnabled(settings, true);
        cordovaWebView.clearCache(true);
        cordovaWebView.addJavascriptInterface(context,"xedjPlugin");
        cordovaWebView.loadUrl(url);
    }
}
