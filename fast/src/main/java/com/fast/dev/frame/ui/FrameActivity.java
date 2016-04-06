package com.fast.dev.frame.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 说明：FrameActivity为Activity基类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:53
 * <p/>
 * 版本：verson 1.0
 */

public abstract class FrameActivity extends AppCompatActivity implements OnClickListener,
        I_Broadcast, I_Activity, I_SkipActivity {


    public static final int WHICH_MSG = 0x100;
    protected FrameFragment currentFragment;
    protected SupportFragment currentSupportFragment;


    /**
     * 说明：一个私有回调类，线程中初始化数据完成后的回调
     */
    private interface ThreadDataCallBack {
        void onSuccess();
    }

    private static ThreadDataCallBack callback;

    // 当线程中初始化的数据初始化完成后，调用回调方法
    private static Handler threadHandle = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == WHICH_MSG) {
                callback.onSuccess();
            }
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentViewBefor();
        if (!AnnotateUtils.initContentView(this)){
            setContentView(setRootView());
        }
        if (useAnnotate()){
            AnnotateUtils.init(this);
        }
        initializer(bundle);
        registerBroadcast();
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bind(int id) {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bind(int id, boolean click) {
        T view = (T) findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }

    @Override
    protected void onDestroy() {
        unRegisterBroadcast();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        clickView(v,v.getId());
    }

    @Override
    public abstract int setRootView();

    @Override
    public abstract void onInit(Bundle bundle);

    @Override
    public void onInitThread() {
        callback = new ThreadDataCallBack() {

            @Override
            public void onSuccess() {
                onInitThreadFinish();
            }
        };
    }

    @Override
    public abstract void clickView(View v,int id);

    @Override
    public void registerBroadcast() {}

    @Override
    public void unRegisterBroadcast() {}

    /**
     * 说明：使用注解
     * @return
     */
    protected boolean useAnnotate(){
        return false;
    }

    /**
     * 说明:设置界面之前调用
     * @return
     */
    protected void setContentViewBefor(){}

    /**
     * 说明：初始化工作
     * @param bundle
     */
    private void initializer(Bundle bundle) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                onInitThread();
                threadHandle.sendEmptyMessage(WHICH_MSG);
            }
        }).start();
        if (getIntent() != null){
            getIntentData(getIntent());
        }
        onInit(bundle);
    }

    /**
     * 说明：如果调用了onInitThread()，则当数据初始化完成后将回调该方法。
     */
    protected void onInitThreadFinish() {}

    /**
     * 说明：用Fragment替换视图
     * @param srcView 被替换视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int srcView,FrameFragment targetFragment){
        if (targetFragment != null && targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(srcView, targetFragment,targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    /**
     * 说明：用Fragment替换视图
     * @param srcView 被替换视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int srcView, SupportFragment targetFragment) {
        if (targetFragment.equals(currentSupportFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(srcView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentSupportFragment != null
                && currentSupportFragment.isVisible()) {
            transaction.hide(currentSupportFragment);
        }
        currentSupportFragment = targetFragment;
        transaction.commit();
    }
}

