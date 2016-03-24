package com.fast.dev.frame.Adapter.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 说明：AbstractAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/23 23:51
 * <p/>
 * 版本：verson 1.0
 */
public abstract class AbstractAdapter<Item extends IItem> extends RecyclerView.Adapter implements IAdapter<Item> {

    private FastAdapter<Item> mFastAdapter;

    public AbstractAdapter wrap(FastAdapter fastAdapter) {
        this.mFastAdapter = fastAdapter;
        this.mFastAdapter.registerAdapter(this);
        return this;
    }

    public AbstractAdapter wrap(AbstractAdapter abstractAdapter) {
        this.mFastAdapter = abstractAdapter.getFastAdapter();
        this.mFastAdapter.registerAdapter(this);
        return this;
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        if (mFastAdapter != null) {
            mFastAdapter.registerAdapterDataObserver(observer);
        }
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        if (mFastAdapter != null) {
            mFastAdapter.unregisterAdapterDataObserver(observer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mFastAdapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return mFastAdapter.getItemId(position);
    }

    @Override
    public FastAdapter<Item> getFastAdapter() {
        return mFastAdapter;
    }

    @Override
    public Item getItem(int position) {
        return mFastAdapter.getItem(position);
    }

    @Override
    public int getItemCount() {
        return mFastAdapter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mFastAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mFastAdapter.onBindViewHolder(holder, position);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        mFastAdapter.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        mFastAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        mFastAdapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return mFastAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        mFastAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mFastAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mFastAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mFastAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    public void mapPossibleType(Iterable<Item> items) {
        if (items != null) {
            for (Item item : items) {
                mapPossibleType(item);
            }
        }
    }

    public void mapPossibleType(Item item) {
        mFastAdapter.registerTypeInstance(item);
    }

}
