package com.fast.dev.frame.Adapter.recycleview.utils;

import android.support.annotation.NonNull;

import com.fast.dev.frame.Adapter.recycleview.IIdentifyable;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 说明：ID工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 2:12
 * <p/>
 * 版本：verson 1.0
 */
public class IdDistributor {
    private static AtomicLong idDistributor = new AtomicLong(9000000000000000000L);

    /**
     * set an unique identifier for all drawerItems which do not have one set already
     *
     * @param items
     * @return
     */
    public static <T extends IIdentifyable> List<T> checkIds(@NonNull List<T> items) {
        for (T item : items) {
            checkId(item);
        }
        return items;
    }

    /**
     * set an unique identifier for all drawerItems which do not have one set already
     *
     * @param items
     * @return
     */
    public static <T extends IIdentifyable> T[] checkIds(@NonNull T... items) {
        for (T item : items) {
            checkId(item);
        }
        return items;
    }

    /**
     * set an unique identifier for the drawerItem which do not have one set already
     *
     * @param item
     * @return
     */
    public static <T extends IIdentifyable> T checkId(@NonNull T item) {
        if (item.getIdentifier() == -1) {
            item.withIdentifier(idDistributor.incrementAndGet());
        }
        return item;
    }
}
