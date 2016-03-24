package com.fast.dev.frame.http.download;

import android.content.Context;

import com.fast.dev.frame.utils.Constant;
import com.fast.dev.frame.utils.FileUtils;
import com.fast.dev.frame.utils.LogUtils;
import com.fast.dev.frame.utils.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 说明：下载管理器
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/8 20:20
 * <p/>
 * 版本：verson 1.0
 */
public class DownloadManager implements DownloadNextTaskListener{

    //所有下载任务
    private List<DownloadInfo> mAllTasks;
    //正在下载的列表
    private List<DownloadInfo> mDownloadingTasks;
    //已经暂停的列表
    private List<DownloadInfo> mPausingTasks;
    //已经完成的列表
    private List<DownloadInfo> mCompleteTasks;
    //等待任务集合
    private ConcurrentLinkedQueue<DownloadInfo> mWaitTasks;

    //下载任务回调集合
    private Map<String,List<DownLoadListener>> mListenerListMap;

    private DownloadUIHandler mDownloadUIHandler;
    private String mTargetFolder;
    private String mTargetName;
    private static DownloadManager sDownloadManager;
    private Map<String,DownloadHttpTask> mDownloadingTaskMap;
    private Object mIteratorLock = new Object();
    private Object mNextTaskLockObj = new Object();
    private boolean mNextTaskLock;

    private DownloadManager(Context context){
        mAllTasks = Collections.synchronizedList(new ArrayList<DownloadInfo>());
        mDownloadingTasks = Collections.synchronizedList(new ArrayList<DownloadInfo>());
        mPausingTasks = Collections.synchronizedList(new ArrayList<DownloadInfo>());
        mCompleteTasks = Collections.synchronizedList(new ArrayList<DownloadInfo>());
        mWaitTasks = new ConcurrentLinkedQueue<>();
        mDownloadingTaskMap = Collections.synchronizedMap(new HashMap<String, DownloadHttpTask>());
        mListenerListMap = Collections.synchronizedMap(new HashMap<String, List<DownLoadListener>>());
        mDownloadUIHandler = new DownloadUIHandler(mListenerListMap);
        setDefalutFolder();
        mTargetName = "";
    }

    public static DownloadManager getInstance(Context context){
        if (null == sDownloadManager){
            sDownloadManager = new DownloadManager(context);
        }
        return sDownloadManager;
    }

    /**
     * 说明：设置目标文件夹
     * @param str
     */
    public DownloadManager setFolder(String str){
        if (StringUtils.isEmpty(str)){
            setDefalutFolder();
        }else {
            File file = FileUtils.makeFolders(str);
            if (file != null){
                mTargetFolder = file.getAbsolutePath();
            }else {
                //设置默认路径
                setDefalutFolder();
            }
        }
        return this;
    }

    /**
     * 说明：设置文件名
     * @param name
     */
    public DownloadManager setFileName(String name){
        if (name == null){
            name = "";
        }
        mTargetName = name;
        return this;
    }

    /**
     * 说明：设置默认目标文件夹
     *       SD/文件名/download/
     */
    public void setDefalutFolder(){
        mTargetFolder = FileUtils.makeFolders(Constant.FilePath.FILE_DOWNLOAD).getAbsolutePath();
    }

    /**
     * 说明：判断是否有这个任务
     * @param url
     * @return
     */
    public boolean hasTask(String url){
        DownloadInfo info = new DownloadInfo();
        info.setUrl(url);
        return mAllTasks.contains(info);
    }

    /**
     * 说明：是否有下载任务
     * @return
     */
    public boolean hasDownloadTask(){
        return !mDownloadingTasks.isEmpty() || !mPausingTasks.isEmpty() || !mWaitTasks.isEmpty() || !mCompleteTasks.isEmpty();
    }

    /**
     * 说明：添加一个任务
     */
    public void addTask(String url,DownLoadListener listener){
        if (StringUtils.isEmpty(url)){
            LogUtils.d("download url is null");
        }else {
            DownloadInfo info = new DownloadInfo();
            info.setUrl(url);
            if (!hasTask(url)){
                info.setTargetName(mTargetName);
                info.setTargetFolder(mTargetFolder);
                if (mDownloadingTasks.size() < Constant.Http.MAX_DOWNLOAD_COUNT){
                    info.setState(DownloadInfo.DOWNLOADING);
                    mDownloadingTasks.add(info);
                    DownloadHttpTask task = new DownloadHttpTask(info,mDownloadUIHandler,this);
                    mDownloadingTaskMap.put(url,task);
                    addTaskListener(url,listener);
                    task.start();
                }else {
                    //加入等待队列
                    info.setState(DownloadInfo.WAIT);
                    if (mWaitTasks.offer(info)){
                        addTaskListener(url,listener);
                    }
                }
                mAllTasks.add(info);
            }else {
                LogUtils.d("url 任务已经存在");
            }
        }

    }

    /**
     * 说明：重新下载
     * @param url
     */
    public void restartTask(String url){
        Iterator<DownloadInfo> iterator = mPausingTasks.iterator();
        if (!mPausingTasks.isEmpty()){
            synchronized (mIteratorLock){
                while (iterator.hasNext()){
                    DownloadInfo info = iterator.next();
                    if (StringUtils.isEquals(url,info.getUrl())){
                        if (mDownloadingTasks.size() < Constant.Http.MAX_DOWNLOAD_COUNT){
                            info.setState(DownloadInfo.DOWNLOADING);
                            DownloadHttpTask task = new DownloadHttpTask(info,mDownloadUIHandler,this);
                            mDownloadingTaskMap.put(url,task);
                            mDownloadingTasks.add(info);
                            iterator.remove();
                            task.start();
                        }else {
                            info.setState(DownloadInfo.WAIT);
                            mWaitTasks.offer(info);
                            iterator.remove();
                        }
                        return;
                    }
                }
            }
        }else {
            stopTask(url);
            restartTask(url);
        }
    }

    /**
     * 说明：停止任务
     * @param url
     * @param remove
     */
    public void stopTask(String url,boolean remove){

    }

    /**
     * 说明：停止任务
     */
    public void stopTask(String url){
        stopTask(url,false);
    }

    private synchronized void removeDownloadingMap(String url){
        synchronized (mIteratorLock){
            Iterator<Map.Entry<String,DownloadHttpTask>> iterator = mDownloadingTaskMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,DownloadHttpTask> entry = iterator.next();
                String key = entry.getKey();
                DownloadHttpTask task = entry.getValue();
                if (StringUtils.isEquals(key,url)){
                    if (task != null){
                        task.setInterrupt(true);
                    }
                    try {
                        iterator.remove();
                    }catch (Exception e){
                        LogUtils.e(e);
                    }
                    break;
                }
            }
        }
    }

    /**
     * 说明：根据URL获取DownloadInfo
     * @param url
     * @return
     */
    public DownloadInfo getDownloadByUrl(String url){
        Iterator<DownloadInfo> iterator = mAllTasks.iterator();
        while (iterator.hasNext()){
            DownloadInfo info = iterator.next();
            if (StringUtils.isEquals(info.getUrl(),url)){
                return info;
            }
        }
        return null;
    }

    public List<DownloadInfo> getAllTask(){
        Collections.sort(mAllTasks);
        return mAllTasks;
    }

    /**
     * 说明：停止所有任务
     */
    public void stopAllTask(){
        //从下载队列移除
        for(Map.Entry<String, DownloadHttpTask> entry:mDownloadingTaskMap.entrySet()){
            DownloadHttpTask task = entry.getValue();
            task.setInterrupt(true);
        }
        mDownloadingTaskMap.clear();

        Iterator<DownloadInfo> downloadingIt = mDownloadingTasks.iterator();
        while (downloadingIt.hasNext()) {
            DownloadInfo b = downloadingIt.next();
            b.setState(DownloadInfo.PAUSE);
            downloadingIt.remove();
            mPausingTasks.add(b);//放入暂停队列
        }
        mDownloadingTasks.clear();

        //从等待队列移除
        Iterator<DownloadInfo> waitIt = mWaitTasks.iterator();
        while (waitIt.hasNext()) {
            DownloadInfo b = waitIt.next();
            b.setState(DownloadInfo.PAUSE);
            waitIt.remove();
            mPausingTasks.add(b);//放入暂停队列
            break;
        }
        mWaitTasks.clear();
    }

    /**
     * 说明：获取下载任务的数量
     * @return
     */
    public int getDownloadingSize(){
        return mDownloadingTasks.size();
    }

    /**
     * 说明：为一个任务添加事件
     * @param url
     * @param listener
     * @return
     */
    public List<DownLoadListener> addTaskListener(String url,DownLoadListener listener){
        List<DownLoadListener> list = mListenerListMap.get(url);
        if (list == null){
            list = new ArrayList<>();
        }
        if (listener != null){
            list.add(listener);
        }
        mListenerListMap.put(url, list);
        return list;
    }

    /**
     * 说明：清除下载回调
     * @param url
     */
    public void clearTaskListener(String url){
        List<DownLoadListener> list = mListenerListMap.get(url);
        if (list != null){
            list.clear();
        }
    }

    /**
     * 说明：删除任务
     * @param url
     */
    public void deleteTask(String url){
        stopTask(url,true);
        Iterator<DownloadInfo> iterator = mPausingTasks.iterator();
        synchronized (mIteratorLock){
            while (iterator.hasNext()){
                DownloadInfo info = iterator.next();
                if (StringUtils.isEquals(info.getUrl(),url)){
                    info.setState(DownloadInfo.PAUSE);
                    iterator.remove();
                    break;
                }
            }
        }

        Iterator<DownloadInfo> completeIt = mCompleteTasks.iterator();
        synchronized (mIteratorLock){
            while (completeIt.hasNext()){
                DownloadInfo info = completeIt.next();
                if (StringUtils.isEquals(info.getUrl(),url)){
                    info.setState(DownloadInfo.PAUSE);
                    completeIt.remove();
                    break;
                }
            }
        }

        synchronized (mIteratorLock){
            String filePath = "";
            Iterator<DownloadInfo> allTaskIt = mAllTasks.iterator();
            while(allTaskIt.hasNext()){
                DownloadInfo info = allTaskIt.next();
                if (StringUtils.isEquals(info.getUrl(),url)){
                    info.setState(DownloadInfo.PAUSE);
                    filePath = info.getTargetPath();
                    allTaskIt.remove();
                    break;
                }
            }
            if (!StringUtils.isEmpty(filePath)){
                new File(filePath).delete();
            }
        }

        synchronized (mIteratorLock){
            for (Map.Entry<String,List<DownLoadListener>> entry : mListenerListMap.entrySet()){
                List<DownLoadListener> list = entry.getValue();
                if (list != null){
                    list.clear();
                    list = null;
                }
            }
        }
    }


    @Override
    public void nextTask() {
        synchronized (mNextTaskLockObj){
            if (!mNextTaskLock){
                mNextTaskLock = true;
                synchronized (mIteratorLock){
                    Iterator<DownloadInfo> iterator = mDownloadingTasks.iterator();
                    while(iterator.hasNext()){
                        DownloadInfo info = iterator.next();
                        if (info.getState() == DownloadInfo.PAUSE){
                            mPausingTasks.add(info);
                            try{
                                iterator.remove();
                            }catch (Exception e){
                                LogUtils.e(e);
                            }
                        }else if (info.getState() == DownloadInfo.COMPLETE){
                            mCompleteTasks.add(info);
                            try{
                                iterator.remove();
                            }catch (Exception e){
                                LogUtils.e(e);
                            }
                        }
                    }
                }
                synchronized (mIteratorLock){
                    Iterator<Map.Entry<String,DownloadHttpTask>> iterator = mDownloadingTaskMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String,DownloadHttpTask> entry = iterator.next();
                        DownloadHttpTask task = entry.getValue();
                        if (task != null && task.isInterrupt()){
                            try {
                                iterator.remove();
                            }catch (Exception e){
                                LogUtils.e(e);
                            }
                        }
                    }
                }
                executeNextTask();
                mNextTaskLock = false;
            }
        }
    }

    private void executeNextTask(){
        if (mDownloadingTasks.size() < Constant.Http.MAX_DOWNLOAD_COUNT){
            if (!mWaitTasks.isEmpty()){
                DownloadInfo info = mWaitTasks.poll();
                if (info != null){
                    String url = info.getUrl();
                    info.setState(DownloadInfo.DOWNLOADING);
                    DownloadHttpTask task = new DownloadHttpTask(info,mDownloadUIHandler,this);
                    mDownloadingTaskMap.put(url,task);
                    task.start();
                }
            }
        }else {
            LogUtils.d("已经达到最大下载数量");
        }
    }
}
