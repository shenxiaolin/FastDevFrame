package com.fast.dev.frame.http.download;

/**
 * 说明：文件下载事件回调
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/8 21:11
 * <p/>
 * 版本：verson 1.0
 */
public abstract class DownLoadListener {

    /**
     * 说明：下载进度
     *
     * @param info
     */
    public abstract void onProgress(DownloadInfo info);

    /**
     * 说明：下载完成时回调
     *
     * @param info
     */
    public abstract void onFinish(DownloadInfo info);

    /**
     * 说明：下载出错时回调
     * @param info
     */
    public abstract void onError(DownloadInfo info);
}
