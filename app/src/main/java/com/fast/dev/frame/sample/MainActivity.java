package com.fast.dev.frame.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fast.dev.frame.ui.Bind;
import com.fast.dev.frame.ui.ContentView;
import com.fast.dev.frame.ui.Toast;
import com.fast.dev.frame.ui.V;

@ContentView(R.layout.activity_main)
public class MainActivity extends CommonActivity {

    @Bind(id = R.id.tv,click = true)
    private TextView tv;

    @Override
    public void onInit(Bundle bundle) {
        super.onInit(bundle);
        V.setText(tv,R.string.tv_main);
    }

    @Override
    public void clickView(View v,int id) {
        switch (id){
            case R.id.tv:
                Toast.get().shortToast(R.string.tv_main);
                break;
        }
    }
}
