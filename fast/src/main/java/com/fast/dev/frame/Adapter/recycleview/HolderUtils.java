package com.fast.dev.frame.Adapter.recycleview;

import android.widget.TextView;

/**
 * 说明：Holder工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 1:51
 * <p/>
 * 版本：verson 1.0
 */
public class HolderUtils {

    public static void setText(TextView tv,String text){
        if (tv != null){
            tv.setText(text);
        }
    }

    public static void setText(TextView tv,int res){
        if (tv != null){
            tv.setText(res);
        }
    }

}
