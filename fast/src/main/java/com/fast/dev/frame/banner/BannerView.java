package com.fast.dev.frame.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fast.dev.frame.R;
import com.fast.dev.frame.banner.listener.OnItemClickListener;
import com.fast.dev.frame.banner.view.BannerViewPager;

import java.lang.ref.WeakReference;

/**
 * 说明：自动轮播控件
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/8 16:25
 * <p/>
 * 版本：verson 1.0
 */
public class BannerView<T> extends LinearLayout {

    //是否可以左右滑动
    private boolean canLoop = true;
    //标记是否正在自动循环
    private boolean isLoop;

    private BannerViewPager mViewPager;
    private LinearLayout ll_dots;

    private AutoBannerTask mAutonBannerTask;

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        canLoop = attrs.getAttributeBooleanValue(R.styleable.BannerView_canLoop,true);
        array.recycle();
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        canLoop = attrs.getAttributeBooleanValue(R.styleable.BannerView_canLoop,true);
        array.recycle();
        init(context);
    }

     @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BannerView);
        canLoop = a.getBoolean(R.styleable.BannerView_canLoop, true);
        a.recycle();
        init(context);
    }
    /**
     * 说明：初始化
     * @param context
     */
    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_banner, this, true);
        mViewPager = (BannerViewPager)view.findViewById(R.id.bannerViewPager);
        ll_dots = (LinearLayout)view.findViewById(R.id.ll_point);
        mAutonBannerTask = new AutoBannerTask(this);
    }

    static class AutoBannerTask implements Runnable{

        private final WeakReference<BannerView> bannerViewReference;

        public AutoBannerTask(BannerView bannerView){
            this.bannerViewReference = new WeakReference<BannerView>(bannerView);
        }

        @Override
        public void run() {
            BannerView bannerView = this.bannerViewReference.get();
            asdf
        }
    }

    /**
     * 说明：控制指示器是否显示
     * @param isVisible
     * @return
     */
    public BannerView setPointVisible(boolean isVisible){
        ll_dots.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 说明：是否开启轮播
     * @return
     */
    public boolean isCanLoop(){
        return mViewPager.isCanLoop();
    }

    /**
     * 说明：设置是否要循序，在设置数据之前调用
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop){
        this.canLoop = canLoop;
        mViewPager.setCanLoop(canLoop);
    }

    /**
     * 说明：获取ViewPager
     * @return
     */
    public BannerViewPager getViewPager(){
        return mViewPager;
    }

    /**
     * 说明：设置Item的点击
     * @param listener
     * @return
     */
    public BannerView setOnItemClickListener(OnItemClickListener listener){
        mViewPager.setOnItemClickListener(listener);
        return this;
    }
}
