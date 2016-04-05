package com.fast.dev.frame.sample;

import android.os.Bundle;

import com.fast.dev.frame.tools.BackTools;
import com.fast.dev.frame.tools.SimpleBackExit;
import com.fast.dev.frame.ui.ContentView;

import butterknife.ButterKnife;
import butterknife.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends CommonActivity {


    @Override
    public void onInit(Bundle bundle) {
        super.onInit(bundle);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.jjtv)
    public void onClick() {
        showActivity(JJActivity.class);
    }

    @Override
    public void onBackPressed() {
        BackTools.onBackPressed(new SimpleBackExit());
    }
}
