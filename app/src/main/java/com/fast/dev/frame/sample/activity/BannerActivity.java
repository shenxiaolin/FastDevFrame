package com.fast.dev.frame.sample.activity;

import android.os.Bundle;
import android.view.View;

import com.fast.dev.frame.banner.BannerView;
import com.fast.dev.frame.banner.holder.BannerHolderCreator;
import com.fast.dev.frame.banner.listener.OnItemClickListener;
import com.fast.dev.frame.sample.Bean.BannerBean;
import com.fast.dev.frame.sample.R;
import com.fast.dev.frame.sample.banner.BannerImageHolder;
import com.fast.dev.frame.ui.ContentView;
import com.fast.dev.frame.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：图片轮播
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/8 16:22
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_banner)
public class BannerActivity extends CommonActivity {

    @Bind(R.id.bannerView)
    BannerView bannerView;

    private long time = 2000;

    private List<BannerBean> beans = new ArrayList<>();

    @Override
    public void onInit(Bundle bundle) {
        super.onInit(bundle);
        ButterKnife.bind(this);
        getDatas();

        bannerView.setPages(new BannerHolderCreator<BannerImageHolder>() {
            @Override
            public BannerImageHolder createHolder() {
                return new BannerImageHolder();
            }
        }, beans);
        bannerView.setPoint(R.mipmap.home_banner_selected, R.mipmap.home_banner_normal);
        bannerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                shortToast(beans.get(position).getDes());
                ToolUtils.openBrowser(BannerActivity.this,beans.get(position).getLink());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerView.start(time);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bannerView.stop();
    }

    private void getDatas(){
        beans.add(new BannerBean("http://img5.imgtn.bdimg.com/it/u=161888459,1712714238&fm=21&gp=0.jpg","http://www.baidu.com","title---0"));
        beans.add(new BannerBean("http://img1.imgtn.bdimg.com/it/u=228196693,618638496&fm=21&gp=0.jpg","http://www.baidu.com","title---1"));
        beans.add(new BannerBean("http://img5.imgtn.bdimg.com/it/u=1911801450,578221691&fm=21&gp=0.jpg","http://www.baidu.com","title---2"));
    }

    @OnClick({R.id.btn_add, R.id.btn_stop,R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                beans.clear();
                beans.add(new BannerBean("http://img1.imgtn.bdimg.com/it/u=1248462995,728310824&fm=21&gp=0.jpg","http://www.baidu.com","title---3"));
                bannerView.notifyDataSetChanged();
                break;
            case R.id.btn_stop:
                bannerView.stop();
                break;
            case R.id.btn_start:
                bannerView.start(time);
                break;
        }
    }
}
