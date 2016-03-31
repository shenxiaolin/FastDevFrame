package com.fast.dev.frame.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fast.dev.frame.HttpUtils;
import com.fast.dev.frame.http.callback.StringHttpCallBack;
import com.fast.dev.frame.ui.BindView;
import com.fast.dev.frame.ui.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends CommonActivity {

    @BindView(id = R.id.tv,click = true)
    private TextView tv;

    @Override
    public void onInit(Bundle bundle) {
        super.onInit(bundle);
        tv.setText(R.string.tv_main);
    }

    @Override
    public void clickView(View v,int id) {
        switch (id){
            case R.id.tv:
                HttpUtils.post("https://www.baidu.com", new StringHttpCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        tv.setText(result);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        tv.setText(msg);
                    }

                    @Override
                    public void onProgress(int progress, long networkSpeed, boolean done) {
                        super.onProgress(progress, networkSpeed, done);
                        tv.setText("progress:"+progress+"  networkSpeed:"+networkSpeed+" 是否完成："+done);
                    }
                });
//                Toast.get().shortToast(R.string.tv_main);
                break;
        }
    }
}
