package com.fast.dev.frame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.fast.dev.frame.listener.ProgressWebViewListener;


/**
 * 说明：带进度的webview
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/19 16:28
 * <p/>
 * 版本：verson 1.0
 */
public class ProgressWebView extends WebView{

    private ProgressWebViewListener mListener;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient());
        removeJavascriptInterface("searchBoxJavaBredge_");
        // 是否可以缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
    }

    public void setProgressListener(ProgressWebViewListener listener){
        this.mListener = listener;
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mListener != null){
                mListener.onProgress(view,newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
