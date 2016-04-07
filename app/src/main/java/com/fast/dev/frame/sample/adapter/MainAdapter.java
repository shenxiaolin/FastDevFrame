package com.fast.dev.frame.sample.adapter;

import android.support.v7.widget.RecyclerView;

import com.fast.dev.frame.Adapter.recycleview.BaseRecyclerAdapter;
import com.fast.dev.frame.Adapter.recycleview.RecyclerViewHolder;
import com.fast.dev.frame.sample.Bean.MainBean;
import com.fast.dev.frame.sample.R;

import java.util.List;

/**
 * 说明：MainAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/6 17:25
 * <p/>
 * 版本：verson 1.0
 */
public class MainAdapter extends BaseRecyclerAdapter<MainBean>{

    public MainAdapter(RecyclerView recyclerView,List<MainBean> data) {
        super(recyclerView,data);
    }

    @Override
    public void convert(RecyclerViewHolder holder, MainBean item, int position) {
        holder.setText(R.id.tv,item.getDes());
        holder.setImage(R.id.iv, item.getUrl());
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_one;
    }

}
