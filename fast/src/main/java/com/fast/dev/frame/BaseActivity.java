package com.fast.dev.frame;

import android.content.Intent;
import com.fast.dev.frame.http.HttpTaskKey;
import com.fast.dev.frame.ui.AbstractActivity;
import com.fast.dev.frame.ui.Toast;

/**
 * 说明：Activity基类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 11:29
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BaseActivity extends AbstractActivity implements HttpTaskKey {

    protected final String HTTP_TASK_KEY = "key_"+hashCode();
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public int setRootView() {
        return 0;
    }

    @Override
    public void getIntentData(Intent intent) {

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

}
