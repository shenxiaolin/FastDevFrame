package com.fast.dev.frame.utils;

import java.io.File;

/**
 * 说明：常用变量
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/26 23:57
 * <p/>
 * 版本：verson 1.0
 */
public final class Constant {

    /******************************* 文件路径 ****************************************/

    public static class FilePath{
        public static final String appname = "wxtt";
        // "/"
        public static final String tor = File.separator;
        // 本应用缓存根目录
        public static final String ROOT = SDCardUtils.getExternalStorage() + tor + appname + tor;
        // 请求网络缓存路径
        public static final String HTTPCONFIG_CACHEPATH = ROOT + "net" + tor;
        // 异常日志缓存路径
        public static final String CRASHHANDLER_CACHEPATH = ROOT + "log" + tor;
        // 请求图片缓存路劲
        public static final String BITMAP_CACHEPATH = ROOT + "image" + tor;
        // 错误日志缓存路径
        public static final String ERROR_CACHEPATH = ROOT + "error" + tor;
        // 升级文件路径
        public static final String APP_DOWNLOAD = ROOT + "app" + tor;
        // 存储下载文件路径
        public static final String FILE_DOWNLOAD = ROOT + "download" + tor;
        // 优酷视频路径
        public static final String YOUKU_VIDEO = tor + appname + tor + "video" + tor;
    }


    /******************************* Bmob ****************************************/
    public static class Bmob{
        public static final String BQL_CHECK_VERSION = "select * from t_version order by createdAt desc";
    }

    /******************************* 网络连接 ****************************************/
    public static class Http{
        // 网络请求超时时间 10s
        public static final int TIMEOUT = 10000;
        // 网络请求默认key
        public static final String DEFAULT_KEY = "defalut_key";
        // 网络请求方式：post
        public static final String POST = "POST";
        // 网络请求方式：get
        public static final String GET = "GET";
        // 网络请求方式：put
        public static final String PUT = "PUT";
        // 网络请求方式：delete
        public static final String DELETE = "DELETE";
        // 网络请求方式：head
        public static final String HEAD = "HEAD";
        // 网络请求方式：patch
        public static final String PATCH = "PATCH";
        // 最大下载数量
        public static final int MAX_DOWNLOAD_COUNT = 10;
    }


}

