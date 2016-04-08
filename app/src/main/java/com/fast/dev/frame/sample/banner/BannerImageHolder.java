package com.fast.dev.frame.sample.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.fast.dev.frame.banner.holder.Holder;
import com.fast.dev.frame.glide.GlideLoader;
import com.fast.dev.frame.sample.Bean.BannerBean;

/**
 * 说明：BannerImageHolder
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/8 21:30
 * <p/>
 * 版本：verson 1.0
 */
public class BannerImageHolder implements Holder<BannerBean>{

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void convert(Context context, int position, BannerBean item) {
        GlideLoader.into(item.getPicUrl(),imageView);
    }
}
