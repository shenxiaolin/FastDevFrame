package com.fast.dev.frame.http.download;

import com.fast.dev.frame.utils.StringUtils;

/**
 * 说明：文件下载数据模型
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/8 20:50
 * <p/>
 * 版本：verson 1.0
 */
public class DownloadInfo implements Comparable<DownloadInfo>{

    //-------------------文件状态-----------------
    //等待中
    public static final int WAIT = 0;
    //下载中
    public static final int DOWNLOADING = 1;
    //暂停中
    public static final int PAUSE = 2;
    //下载完成
    public static final int COMPLETE = 3;

    private int id;
    private String url;//文件url
    private String targetPath;//保存文件地址
    private String targetFolder;//保存文件夹
    private String targetName;//文件名
    private int progress;//下载进度
    private long totalLength;//总大小
    private long downloadLength;//已经下载文件大小
    private long networkSpeed;//下载速度
    private int state = PAUSE;

    public static int getPAUSE() {
        return PAUSE;
    }

    public static int getWAIT() {
        return WAIT;
    }

    public static int getDOWNLOADING() {
        return DOWNLOADING;
    }

    public static int getCOMPLETE() {
        return COMPLETE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public long getNetworkSpeed() {
        return networkSpeed;
    }

    public void setNetworkSpeed(long networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DownloadInfo){
            DownloadInfo info = (DownloadInfo)o;
            return info != null && !StringUtils.isEmpty(info.getUrl());
        }else {
            return false;
        }
    }

    @Override
    public int compareTo(DownloadInfo another) {
        return (another == null) ? 0 : (getId() - another.getId());
    }
}
