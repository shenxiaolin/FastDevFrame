package com.fast.dev.frame;

import android.content.Context;
import android.text.TextUtils;

import com.fast.dev.frame.http.RequestParams;
import com.fast.dev.frame.http.callback.BaseHttpCallBack;
import com.fast.dev.frame.http.download.DownloadManager;
import com.fast.dev.frame.http.HttpConfig;
import com.fast.dev.frame.http.HttpRequest;
import com.fast.dev.frame.http.download.DownLoadListener;

/**
 * 说明：Http请求网络工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 12:11
 * <p/>
 * 版本：verson 1.0
 */
public class HttpUtils {

    //网络配置器
    private static HttpConfig sConfig = null;

    /**
     * 说明：禁止实例化
     */
    private HttpUtils(){}

    /**
     * 说明：发送post请求
     * @param url
     */
    public static void post(String url){
        HttpRequest.post(url);
    }

    /**
     * 说明：发送post请求
     * @param url
     * @param params
     */
    public static void post(String url,RequestParams params){
        HttpRequest.post(url,params);
    }

    /**
     * 说明：发送post请求
     * @param url
     * @param callBack
     */
    public static void post(String url,BaseHttpCallBack callBack){
        HttpRequest.post(url,callBack);
    }

    /**
     * 说明：发送post请求
     * @param url
     * @param params
     * @param callBack
     */
    public static void post(String url,RequestParams params,BaseHttpCallBack callBack){
        HttpRequest.post(url,params,callBack);
    }

    /**
     * 说明：发送get请求
     * @param url
     */
    public static void get(String url){
        HttpRequest.get(url);
    }

    /**
     * 说明：发送get请求
     * @param url
     * @param params
     */
    public static void get(String url,RequestParams params){
        HttpRequest.get(url, params);
    }

    /**
     * 说明：发送get请求
     * @param url
     * @param callBack
     */
    public static void get(String url,BaseHttpCallBack callBack){
        HttpRequest.get(url, callBack);
    }

    /**
     * 说明：发送get请求
     * @param url
     * @param params
     * @param callBack
     */
    public static void get(String url,RequestParams params,BaseHttpCallBack callBack){
        HttpRequest.get(url, params, callBack);
    }

    /**
     * 说明：发送put请求
     * @param url
     */
    public static void put(String url){
        HttpRequest.put(url);
    }

    /**
     * 说明：发送put请求
     * @param url
     * @param params
     */
    public static void put(String url,RequestParams params){
        HttpRequest.put(url, params);
    }

    /**
     * 说明：发送put请求
     * @param url
     * @param callBack
     */
    public static void put(String url,BaseHttpCallBack callBack){
        HttpRequest.put(url, callBack);
    }

    /**
     * 说明：发送put请求
     * @param url
     * @param params
     * @param callBack
     */
    public static void put(String url,RequestParams params,BaseHttpCallBack callBack){
        HttpRequest.put(url, params, callBack);
    }

    /**
     * 说明：发送head请求
     * @param url
     */
    public static void head(String url){
        HttpRequest.head(url);
    }

    /**
     * 说明：发送head请求
     * @param url
     * @param params
     */
    public static void head(String url,RequestParams params){
        HttpRequest.head(url, params);
    }

    /**
     * 说明：发送head请求
     * @param url
     * @param callBack
     */
    public static void head(String url,BaseHttpCallBack callBack){
        HttpRequest.head(url, callBack);
    }

    /**
     * 说明：发送head请求
     * @param url
     * @param params
     * @param callBack
     */
    public static void head(String url,RequestParams params,BaseHttpCallBack callBack){
        HttpRequest.head(url, params, callBack);
    }

    /**
     * 说明：发送delete请求
     * @param url
     */
    public static void delete(String url){
        HttpRequest.delete(url);
    }

    /**
     * 说明：发送delete请求
     * @param url
     * @param params
     */
    public static void delete(String url,RequestParams params){
        HttpRequest.delete(url, params);
    }

    /**
     * 说明：发送delete请求
     * @param url
     * @param callBack
     */
    public static void delete(String url,BaseHttpCallBack callBack){
        HttpRequest.delete(url, callBack);
    }

    /**
     * 说明：发送delete请求
     * @param url
     * @param params
     * @param callBack
     */
    public static void delete(String url,RequestParams params,BaseHttpCallBack callBack){
        HttpRequest.delete(url, params, callBack);
    }

    /**
     * 说明：发送patch请求
     * @param url
     */
    public static void patch(String url){
        HttpRequest.patch(url);
    }

    /**
     * 说明：发送patch请求
     * @param url
     * @param params
     */
    public static void patch(String url,RequestParams params){
        HttpRequest.patch(url, params);
    }

    /**
     * 说明：发送patch请求
     * @param url
     * @param callBack
     */
    public static void patch(String url,BaseHttpCallBack callBack){
        HttpRequest.patch(url, callBack);
    }

    /**
     * 说明：发送patch请求
     * @param url
     * @param params
     * @param callBack
     */
    public static void patch(String url,RequestParams params,BaseHttpCallBack callBack){
        HttpRequest.patch(url, params, callBack);
    }

    /**
     * 说明：下载文件
     * @param path
     * @param name
     * @param url
     * @param listener
     */
    public static void download(Context context,String path,String name,String url,DownLoadListener listener){
        if (!TextUtils.isEmpty(url)){
            if (!DownloadManager.getInstance(context).hasTask(url)){
                DownloadManager.getInstance(context).setFolder(path).setFileName(name).addTask(url,listener);
            }
        }
    }

    /**
     * 说明：取消一个请求
     * @param url
     */
    public static void cancel(String url){
        HttpRequest.cancel(url);
    }

    /**
     * 说明：下载文件
     *       默认路径SD/应用名/download/
     * @param context
     * @param url
     * @param listener
     */
    public static void download(Context context,String url,DownLoadListener listener){
        download(context, "", "", url, listener);
    }

    /**
     * 说明：暂停下载任务
     * @param url
     */
    public static void stopTask(Context context,String url){
        if (TextUtils.isEmpty(url)){
            return;
        }
        if (DownloadManager.getInstance(context).hasTask(url)){
            DownloadManager.getInstance(context).stopTask(url);
        }
    }

    /**
     * 说明：继续下载文件
     * @param context
     * @param url
     */
    public static void restartTask(Context context,String url){
        if (TextUtils.isEmpty(url)){
            return;
        }
        if (DownloadManager.getInstance(context).hasTask(url)){
            DownloadManager.getInstance(context).restartTask(url);
        }
    }

}
