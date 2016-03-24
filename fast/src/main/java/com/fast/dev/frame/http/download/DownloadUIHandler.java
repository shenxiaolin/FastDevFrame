package com.fast.dev.frame.http.download;

import android.os.Handler;
import android.os.Message;

import java.util.List;
import java.util.Map;

/**
 * 说明：DownloadUIHandler处理器
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/8 21:25
 * <p/>
 * 版本：verson 1.0
 */
public class DownloadUIHandler extends Handler{

    private Map<String,List<DownLoadListener>> mListenerListMap;
    private DownLoadListener mGlobalListener;

    public DownloadUIHandler(Map<String,List<DownLoadListener>> map){
        this.mListenerListMap = map;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        DownloadInfo info = (DownloadInfo)msg.obj;
        if (info != null){
            refreshDwonloadInfo(info);
        }
    }

    private void refreshDwonloadInfo(DownloadInfo info){
        List<DownLoadListener> mListListener = mListenerListMap.get(info.getUrl());
        if (mListListener != null && !mListListener.isEmpty()){
            for (DownLoadListener listener : mListListener){
                if (listener != null){
                    executeListener(listener,info);
                }
            }
        }
    }

    private void executeListener(DownLoadListener listener,DownloadInfo info){
        int state = info.getState();
        switch (state){
            case DownloadInfo.COMPLETE:
                listener.onFinish(info);
                break;
            case DownloadInfo.WAIT:
            case DownloadInfo.DOWNLOADING:
                listener.onProgress(info);
                break;
            case DownloadInfo.PAUSE:
                listener.onError(info);
                break;
        }
    }
}
