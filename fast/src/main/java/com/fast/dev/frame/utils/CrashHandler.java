package com.fast.dev.frame.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;

import com.fast.dev.frame.ui.ActivityStack;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明：手机日志的工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:16
 * <p/>
 * 版本：verson 1.0
 */

public class CrashHandler implements UncaughtExceptionHandler {

    private static String crashFile;
    protected static Context mContext;
    // 错误日志文件名
    private String fileName = "";
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 说明：禁止实例化
     */
    public CrashHandler() {
        init();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //生成错误日志文件名
        fileName = AndroidInfoUtils.getAndroidId(mContext)+"_crash_"
                + DateUtils.getNowTime(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_4)+".txt";
        // 处理异常
        handleException(ex, crashFile);
        SystemClock.sleep(3000);
        // 退出应用
        ActivityStack.create().AppExit();
    }

    public static void create(Context context,String filePath) {
        crashFile = filePath;
        mContext = context;
    }

    private void init(){
        Thread.setDefaultUncaughtExceptionHandler(setHandler());
    }

    /**
     * 说明：设置处理器
     */
    public UncaughtExceptionHandler setHandler(){
        return this;
    }

    /**
     * 说明：自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true代表处理该异常，不再向上抛异常，
     *         false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     *         简单来说就是true不会弹出那个错误提示框，false就会弹出
     */
    private boolean handleException(final Throwable ex, String filePath) {
        if (ex == null) {
            return false;
        }
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        String upFile = saveCrashInfo2File(ex, filePath);
        upLog(new File(upFile));
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                show();
                Looper.loop();
            }

        }.start();
        return false;
    }

    /**
     * 说明：收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
    }

    /**
     * 说明：保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex, String filePath) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        sb.toString();
        FileUtils.saveFileCache(sb.toString().getBytes(), filePath, fileName);
        return filePath + fileName;
    }

    /**
     * 说明：向服务器发送错误日志
     *
     * @param file
     */
    public void upLog(File file) {}

    /**
     * 说明：程序崩溃退出时调用
     */
    public void show(){}

}

