package com.fast.dev.frame.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.fast.dev.frame.FastFrame;

import java.io.File;

/**
 * 说明：手机信息相关工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/26 23:57
 * <p/>
 * 版本：verson 1.0
 */

public final class AndroidInfoUtils {

    private AndroidInfoUtils() {}

    /**
     * 说明：获取手机网络状态是否可用
     *
     * @return 返回网络状态【true:网络联通】【false:网络断开】
     */
    public static boolean isNetConnected() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) FastFrame.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            if (connectivityManager != null) {
                if (network != null && network.isConnected()) {
                    if (network.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 说明：获取手机IMEI号码
     *
     * @return 返回手机IMEI号码
     */
    public static String getImeiCode() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) FastFrame.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 说明：获取手机IMSI号码
     *
     * @return 返回手机IMEI号码
     */
    public static String getImsiCode() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) FastFrame.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取手机Android_ID
     *
     * @return MacAddress String
     */
    public static String getAndroidId() {
        String androidId = Secure.getString(FastFrame.getContext().getContentResolver(),
                Secure.ANDROID_ID);
        return androidId;
    }

    /**
     * 说明：获取本机手机号码
     *
     * @return 返回本机手机号码
     */
    public static String getMobilNumber() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) FastFrame.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 说明：获取系统信息
     *
     * @return
     */
    public static String getOs() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 说明：获取手机MAC地址
     *
     * @return 返回手机MAC地址
     */
    public static String getMacAddress() {
        String res = "";
        try {
            final WifiManager wifiManager = (WifiManager) FastFrame.getContext()
                    .getSystemService(Context.WIFI_SERVICE);
            final WifiInfo info = wifiManager.getConnectionInfo();
            if (null != info) {
                res = info.getMacAddress();
            }
            if (TextUtils.isEmpty(res)) {
                res = "00:00:00:00:00:00";
            }
        } catch (Exception e) {
            res = "";
        }
        return res;
    }

    /**
     * 说明：获取当前应用程序的VersionName
     *
     *            当前上下文环境
     * @return 返回当前应用的版本号
     */
    public static String getAppVersionName() {
        try {
            PackageInfo info = FastFrame.getContext().getPackageManager().getPackageInfo(
                    FastFrame.getContext().getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 说明：获取当前应用程序的VersionCode
     *
     * @return 返回当前应用的版本号
     */
    public static int getAppVersionCode() {
        try {
            PackageInfo info = FastFrame.getContext().getPackageManager().getPackageInfo(
                    FastFrame.getContext().getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 说明：获取当前网络类型
     *      字段常量[NetWorkType.NETTYPE_WIFI]
     * @return 0：没有网络 1：WIFI网络 2：2G网络 3：3G网络 4:4G网络
     *         int NETTYPE_NONET = 0;
     *         int NETTYPE_WIFI = 1;
     *         int NETTYPE_2G = 2;
     *         int NETTYPE_3G = 3;
     *         int NETTYPE_4G = 4;
     */
    public static int getNetWorkType() {
        int strNetworkType = 0;
        NetworkInfo networkInfo = ((ConnectivityManager) FastFrame.getContext().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = NetWorkType.NETTYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        strNetworkType = NetWorkType.NETTYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        strNetworkType = NetWorkType.NETTYPE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        strNetworkType = NetWorkType.NETTYPE_4G;
                        break;
                    default:
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                || _strSubTypeName.equalsIgnoreCase("WCDMA")
                                || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = NetWorkType.NETTYPE_3G;
                        } else {
                            strNetworkType = NetWorkType.NETTYPE_NONET;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 说明：检测手机空间可用大小 get the space is left over on phone self
     */
    public static long getRealSizeOnPhone() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        @SuppressWarnings("deprecation")
        long blockSize = stat.getBlockSize();
        @SuppressWarnings("deprecation")
        long availableBlocks = stat.getAvailableBlocks();
        long realSize = blockSize * availableBlocks;
        return realSize;
    }

    /**
     * 说明：获取设备终端码
     *
     * @return
     */
    public static String getTerminalCode() {
        try {
            return  MD5.getMD5(getImeiCode() + getImsiCode() + getAndroidId());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备的可用内存大小
     *
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory() {
        ActivityManager am = (ActivityManager) FastFrame.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // 返回当前系统的可用内存
        return (int) (mi.availMem / (1024 * 1024));
    }

    /**
     * 说明：获取当前线程名称
     * @return
     */
    public static String getCurProcessName(){
        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) FastFrame.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /******************************* 网络类型 ****************************************/

    public static class NetWorkType{
        // 网络类型
        public static final int NETTYPE_NONET = 0;
        public static final int NETTYPE_WIFI = 1;
        public static final int NETTYPE_2G = 2;
        public static final int NETTYPE_3G = 3;
        public static final int NETTYPE_4G = 4;
    }

}
