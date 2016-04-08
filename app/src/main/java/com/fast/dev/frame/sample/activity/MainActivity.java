package com.fast.dev.frame.sample.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fast.dev.frame.Adapter.recycleview.BaseRecyclerAdapter;
import com.fast.dev.frame.sample.Bean.MainBean;
import com.fast.dev.frame.sample.R;
import com.fast.dev.frame.sample.adapter.MainAdapter;
import com.fast.dev.frame.tools.BackTools;
import com.fast.dev.frame.tools.RecyclerTools;
import com.fast.dev.frame.tools.SimpleBackExit;
import com.fast.dev.frame.ui.ContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends CommonActivity {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_emtpy)
    TextView tv_empty;

    private MainAdapter mAdapter;
    private RecyclerTools mRecyclerTools;
    private List<MainBean> mData;

    @Override
    public void onInit(Bundle bundle) {
        super.onInit(bundle);
        ButterKnife.bind(this);

        getDatas();

        mRecyclerTools = new RecyclerTools();
        mAdapter = new MainAdapter(recyclerView,mData);
        mRecyclerTools.setRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
        mAdapter.refresh(mData);
        mAdapter.setEmptyView(tv_empty);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        showActivity(JJActivity.class);
                        break;
                    case 1:
                        showActivity(BannerActivity.class);
                        break;
                    default:
                        shortToast(mData.get(position).getDes());
                        break;
                }
            }
        });
    }

    @OnClick(R.id.btn_add)
    public void addData(){
//        getDatas();
        mData.get(1).setDes("修改数据");
        mAdapter.refresh(mData);
        shortToast("修改成功！");
    }

    public List<MainBean> getDatas(){
        String url = "http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1402/27/c4/31612517_1393474458218_mthumb.jpg";
        mData = new ArrayList<>();
        mData.add(new MainBean(url,"JJSearch"));
        mData.add(new MainBean(url,"Banner展示"));
        for (int i = 1;i < 101;i++){
            mData.add(new MainBean(url,i+""));
        }
        return mData;
    }

    @Override
    public void onBackPressed() {
        BackTools.onBackPressed(new SimpleBackExit());
    }

}
