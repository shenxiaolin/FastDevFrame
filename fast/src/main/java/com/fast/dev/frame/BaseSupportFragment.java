package com.fast.dev.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fast.dev.frame.http.HttpTaskKey;
import com.fast.dev.frame.ui.SupportFragment;
import com.fast.dev.frame.ui.Toast;

/**
 * 说明：Fragment基类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/26 1:23
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BaseSupportFragment extends SupportFragment implements HttpTaskKey {

    private FragmentActivity mActivity;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = getActivity();
        return inflater.inflate(setRootView(),null);
    }

    protected abstract int setRootView();

    @Override
    public String getHttpTaskKey() {
        return "key"+hashCode();
    }

    /***************************************************************************************/

    public void shortToast(int res){
        Toast.get().shortToast(res);
    }
    public void shortToast(String res){
        Toast.get().shortToast(res);
    }
    public void longToast(String res){
        Toast.get().longToast(res);
    }
    public void longToast(int res){
        Toast.get().longToast(res);
    }
    public void cancelToast(){
        Toast.get().cancelToast();
    }

    /***************************************************************************************/

    /***************************************************************************************/

    public void skipActivity(Class<?> cls) {
        skipActivity(cls);
        mActivity.finish();
    }

    public void skipActivity(Intent intent) {
        skipActivity(intent);
        mActivity.finish();
    }

    public void skipActivity(Class<?> cls, Bundle bundle) {
        skipActivity(cls, bundle);
        mActivity.finish();
    }

    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(mActivity,cls);
        mActivity.startActivity(intent);
    }

    public void showActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    public void showActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mActivity,cls);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    /***************************************************************************************/
}
