package com.fast.dev.frame.sample.adapter;

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

    public MainAdapter(int layoutId, List<MainBean> data) {
        super(layoutId, data);
    }

    @Override
    public void convert(RecyclerViewHolder holder, MainBean item, int position) {
        holder.setText(R.id.tv,item.getDes());
        holder.setImage(R.id.iv,item.getUrl());
    }
}
