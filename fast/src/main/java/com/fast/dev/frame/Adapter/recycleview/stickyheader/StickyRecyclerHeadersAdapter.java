package com.fast.dev.frame.Adapter.recycleview.stickyheader;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 说明：StickyRecyclerHeadersAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 1:01
 * <p/>
 * 版本：verson 1.0
 */
public interface StickyRecyclerHeadersAdapter<VH extends RecyclerView.ViewHolder> {
    long getHeaderId(int position);
    VH onCreateHeaderViewHolder(ViewGroup parent);
    void onBindHeaderViewHolder(VH holder, int position);
    int getItemCount();
}
