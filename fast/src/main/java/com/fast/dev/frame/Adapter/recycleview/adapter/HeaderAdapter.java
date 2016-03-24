package com.fast.dev.frame.Adapter.recycleview.adapter;

import com.fast.dev.frame.Adapter.recycleview.IItem;

/**
 * 说明：HeaderAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 2:30
 * <p/>
 * 版本：verson 1.0
 */
public class HeaderAdapter<Item extends IItem> extends ItemAdapter<Item> {

    @Override
    public int getOrder() {
        return 100;
    }
}
