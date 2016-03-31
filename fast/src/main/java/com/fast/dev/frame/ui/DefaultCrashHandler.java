package com.fast.dev.frame.ui;

import com.fast.dev.frame.utils.AndroidInfoUtils;
import com.fast.dev.frame.utils.CrashHandler;
import com.fast.dev.frame.utils.DateUtils;
import com.fast.dev.frame.utils.SDCardUtils;

import java.io.File;

/**
 * 说明：默认的崩溃处理器
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/30 21:04
 * <p/>
 * 版本：verson 1.0
 */
public class DefaultCrashHandler extends CrashHandler{
    @Override
    public void upCrashLog(File file, String error) {

    }

    @Override
    public String setFileName() {
        return AndroidInfoUtils.getAndroidId()+"_crash_" + DateUtils.getNowTime(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_4)+".txt";
    }

    @Override
    public String setCrashFilePath() {
        return SDCardUtils.getExternalStorage() + File.separator;
    }
}
