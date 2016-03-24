package com.fast.dev.frame.Adapter.recycleview;

/**
 * 说明：item的id
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/22 16:23
 * <p/>
 * 版本：verson 1.0
 */
public interface IIdentifyable<T> {


    /**
     * 说明：给item设置标识符
     * @param identifier
     * @return
     */
    T withIdentifier(long identifier);

    /**
     * 说明：获取item标识符
     * @return 未设置返回-1
     */
    long getIdentifier();

}
