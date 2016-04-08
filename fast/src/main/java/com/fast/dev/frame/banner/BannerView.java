package com.fast.dev.frame.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fast.dev.frame.R;
import com.fast.dev.frame.banner.adapter.BannerAdapter;
import com.fast.dev.frame.banner.holder.BannerHolderCreator;
import com.fast.dev.frame.banner.listener.BannerPointPageChangeListener;
import com.fast.dev.frame.banner.listener.OnItemClickListener;
import com.fast.dev.frame.banner.view.BannerViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    //标记是否可以翻页
    private boolean canTurn = false;
    //是否可以左右滑动
    private boolean canLoop = true;
    //标记是否正在自动循环
    private boolean isLoop;
    //指示器选中和未选中的资源图标
    private int selected,normal;
    private List<T> mDatas;
    //延迟循环时间
    private long mDelayTime;

    private BannerPointPageChangeListener pointPageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ArrayList<ImageView> mPointViews = null;
    private BannerAdapter mAdapter;
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
        canLoop = attrs.getAttributeBooleanValue(R.styleable.BannerView_canLoop, true);
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
        mPointViews = new ArrayList<>();
    }

    static class AutoBannerTask implements Runnable{

        private final WeakReference<BannerView> bannerViewReference;

        public AutoBannerTask(BannerView bannerView){
            this.bannerViewReference = new WeakReference<BannerView>(bannerView);
        }

        @Override
        public void run() {
            BannerView bannerView = this.bannerViewReference.get();
            if (bannerView != null && bannerView.mViewPager != null && bannerView.isLoop){
                int page = bannerView.mViewPager.getCurrentItem() + 1;
                bannerView.mViewPager.setCurrentItem(page);
                bannerView.postDelayed(bannerView.mAutonBannerTask,bannerView.mDelayTime);
            }
        }
    }

    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged(){
        mViewPager.getAdapter().notifyDataSetChanged();
        if (selected > 0 && normal > 0)
            setPoint(selected,normal);
    }

    /**
     * 说明：设置数据
     * @param holderCreator
     * @param data
     * @return
     */
    public BannerView setPages(BannerHolderCreator holderCreator,List<T> data){
        this.mDatas = data;
        mAdapter = new BannerAdapter(holderCreator,mDatas);
        mViewPager.setAdapter(mAdapter, canLoop);
        return this;
    }

    /**
     * 说明：设置指示器资源
     * @param selectedRes
     * @param normalRes
     * @return
     */
    public BannerView setPoint(int selectedRes,int normalRes){
        ll_dots.removeAllViews();
        mPointViews.clear();
        this.selected = selectedRes;
        this.normal = normalRes;
        if (mDatas == null || mDatas.isEmpty()){
            return this;
        }
        for (int i = 0;i < mDatas.size(); i++){
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (i == 0)
                pointView.setImageResource(selected);
            else
                pointView.setImageResource(normal);
            mPointViews.add(pointView);
            ll_dots.addView(pointView);
        }
        pointPageChangeListener = new BannerPointPageChangeListener(mPointViews,selected,normal);
        mViewPager.addOnPageChangeListener(pointPageChangeListener);
        pointPageChangeListener.onPageSelected(mViewPager.getRealItem());
        if (onPageChangeListener != null){
            pointPageChangeListener.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
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

    /**
     * 说明：是否正在轮播
     * @return
     */
    public boolean isLoop(){
        return isLoop;
    }

    /**
     * 说明：开启自动轮播
     * @param delayTime
     * @return
     */
    public BannerView start(long delayTime){
        if (isLoop){
            stop();
        }
        //设置可以手动翻页
        canTurn = true;
        this.mDelayTime = delayTime;
        isLoop = true;
        postDelayed(mAutonBannerTask, mDelayTime);
        return this;
    }

    /**
     * 说明：停止自动轮播
     * @return
     */
    public void stop(){
        isLoop = false;
        removeCallbacks(mAutonBannerTask);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                //开始自动轮播
                if (canTurn)start(mDelayTime);
                break;
            case MotionEvent.ACTION_DOWN:
                //停止自动轮播
                if (canTurn)stop();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public int getCurrentItem(){
        if (mViewPager != null){
            return mViewPager.getRealItem();
        }
        return -1;
    }

    public void setCurrentItem(int index){
        if (mViewPager != null){
            mViewPager.setCurrentItem(index);
        }
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener(){
        return onPageChangeListener;
    }

    public BannerView addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        this.onPageChangeListener = onPageChangeListener;
        if (pointPageChangeListener != null){
            pointPageChangeListener.setOnPageChangeListener(onPageChangeListener);
        }else {
            mViewPager.addOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    public boolean isManualPageable() {
        return mViewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        mViewPager.setCanScroll(manualPageable);
    }
}
