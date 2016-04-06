package com.fast.dev.frame.sample.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fast.dev.frame.Adapter.recycleview.BaseRecyclerAdapter;
import com.fast.dev.frame.sample.Bean.MainBean;
import com.fast.dev.frame.sample.R;
import com.fast.dev.frame.sample.adapter.MainAdapter;
import com.fast.dev.frame.tools.BackTools;
import com.fast.dev.frame.tools.SimpleBackExit;
import com.fast.dev.frame.ui.ContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ContentView(R.layout.activity_main)
public class MainActivity extends CommonActivity {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainAdapter mAdapter;
    private List<MainBean> mData;

    @Override
    public void onInit(Bundle bundle) {
        super.onInit(bundle);
        ButterKnife.bind(this);


        mAdapter = new MainAdapter(R.layout.item_main,mData);
        getDatas();
        recyclerView.setAdapter(mAdapter);
        mAdapter.refresh(mData);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        showActivity(JJActivity.class);
                        break;
                    case 1:
                        shortToast(mData.get(position).getDes());
                        break;
                    default:
                        break;
                }
            }
        });
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    public List<MainBean> getDatas(){
        mData = new ArrayList<>();
        mData.add(new MainBean("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg","JJSearch"));
        mData.add(new MainBean("http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg","Hello!"));
        return mData;
    }

    @Override
    public void onBackPressed() {
        BackTools.onBackPressed(new SimpleBackExit());
    }

}
