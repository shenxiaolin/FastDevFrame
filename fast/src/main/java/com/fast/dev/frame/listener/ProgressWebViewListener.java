package com.fast.dev.frame.listener;

import android.webkit.WebView;

/**
 * 说明：webview进度监听
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/20 9:42
 * <p/>
 * 版本：verson 1.0
 */
public interface ProgressWebViewListener {
    void onProgress(WebView webView, int progress);
}
