package com.fast.dev.frame.sample.activity;

import android.os.Bundle;
import android.view.View;

import com.cjj.sva.JJSearchView;
import com.cjj.sva.anim.controller.JJAroundCircleBornTailController;
import com.cjj.sva.anim.controller.JJBarWithErrorIconController;
import com.cjj.sva.anim.controller.JJChangeArrowController;
import com.cjj.sva.anim.controller.JJCircleToBarController;
import com.cjj.sva.anim.controller.JJCircleToLineAlphaController;
import com.cjj.sva.anim.controller.JJCircleToSimpleLineController;
import com.cjj.sva.anim.controller.JJDotGoPathController;
import com.cjj.sva.anim.controller.JJScaleCircleAndTailController;
import com.fast.dev.frame.sample.R;
import com.fast.dev.frame.ui.ContentView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 说明：搜索动画
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/5 13:54
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_jj)
public class JJActivity extends CommonActivity {
    @Bind(R.id.jjsv)
    JJSearchView jjsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        jjsv.setController(new JJChangeArrowController());
    }

    public void start(View v) {
        jjsv.startAnim();
    }

    public void action1(View v) {
        shortToast("JJAroundCircleBornTailController");
        jjsv.setController(new JJAroundCircleBornTailController());
    }

    public void action2(View v) {
        shortToast("JJBarWithErrorIconController");
        jjsv.setController(new JJBarWithErrorIconController());
    }

    public void action3(View v) {
        shortToast("JJChangeArrowController");
        jjsv.setController(new JJChangeArrowController());
    }

    public void action4(View v) {
        shortToast("JJCircleToBarController");
        jjsv.setController(new JJCircleToBarController());
    }

    public void action5(View v) {
        shortToast("JJCircleToLineAlphaController");
        jjsv.setController(new JJCircleToLineAlphaController());
    }

    public void action6(View v) {
        shortToast("JJCircleToSimpleLineController");
        jjsv.setController(new JJCircleToSimpleLineController());
    }

    public void action7(View v) {
        shortToast("JJDotGoPathController");
        jjsv.setController(new JJDotGoPathController());
    }

    public void action8(View v) {
        shortToast("JJScaleCircleAndTailController");
        jjsv.setController(new JJScaleCircleAndTailController());
    }

    public void reset(View v) {
        jjsv.resetAnim();
    }

}
