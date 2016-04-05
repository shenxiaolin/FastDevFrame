package com.fast.dev.frame.interfaces;

/**
 * 说明：退出接口
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/5 16:41
 * <p/>
 * 版本：verson 1.0
 */
public interface BackExit {

    /**
     * 说明：显示退出提示
     */
    void showTips();

    /**
     * 说明：退出调用
     */
    void exit();

    /**
     * 说明：设置等待时间
     * @return
     */
    long setWaitTime();

}
