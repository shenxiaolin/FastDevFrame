package com.fast.dev.frame.database;

import android.content.Context;

import com.fast.dev.frame.DBUtils;


/**
 * 说明：数据库配置器
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 16:09
 * <p/>
 * 版本：verson 1.0
 */
final public class DaoConfig {
    private Context mContext = null; // android上下文
    private String mDbName = "wxtt.db"; // 数据库名字
    private int dbVersion = 1; // 数据库版本
    private boolean debug = true; // 是否是调试模式（调试模式 增删改查的时候显示SQL语句）
    private DBUtils.DbUpdateListener dbUpdateListener;
    private String targetDirectory;// 数据库文件在sd卡中的目录

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * 数据库名
     */
    public String getDbName() {
        return mDbName;
    }

    /**
     * 数据库名
     */
    public void setDbName(String dbName) {
        this.mDbName = dbName;
    }

    /**
     * 数据库版本
     */
    public int getDbVersion() {
        return dbVersion;
    }

    /**
     * 数据库版本
     */
    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    /**
     * 是否调试模式
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * 是否调试模式
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 数据库升级时监听器
     *
     * @return
     */
    public DBUtils.DbUpdateListener getDbUpdateListener() {
        return dbUpdateListener;
    }

    /**
     * 数据库升级时监听器
     */
    public void setDbUpdateListener(DBUtils.DbUpdateListener dbUpdateListener) {
        this.dbUpdateListener = dbUpdateListener;
    }

    /**
     * 数据库文件在sd卡中的目录
     */
    public String getTargetDirectory() {
        return targetDirectory;
    }

    /**
     * 数据库文件在sd卡中的目录
     */
    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }
}
