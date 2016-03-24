package com.fast.dev.frame.Adapter.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 说明：ViewHolderFactory
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 1:16
 * <p/>
 * 版本：verson 1.0
 */
public interface ViewHolderFactory<T extends RecyclerView.ViewHolder> {
    T create(View v);
}
