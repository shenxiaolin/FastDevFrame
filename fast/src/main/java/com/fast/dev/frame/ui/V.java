package com.fast.dev.frame.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fast.dev.frame.utils.UIUtils;

/**
 * 说明：View工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 21:36
 * <p/>
 * 版本：verson 1.0
 */
public class V {

    /**
     * 说明：禁止实例化
     */
    private V(){}

    /**
     * 说明：View赋值
     * @param v
     * @param text
     */
    public static void setText(View v,String text){
        if (v != null){
            if (v instanceof TextView){
                ((TextView)v).setText(TextUtils.isEmpty(text)?"":text);
            }else if (v instanceof Button){
                ((Button)v).setText(TextUtils.isEmpty(text)?"":text);
            }else if (v instanceof CheckBox){
                ((CheckBox)v).setText(TextUtils.isEmpty(text)?"":text);
            }else if (v instanceof EditText){
                ((EditText)v).setText(TextUtils.isEmpty(text)?"":text);
            }else if (v instanceof RadioButton){
                ((RadioButton)v).setText(TextUtils.isEmpty(text)?"":text);
            }
        }
    }

    /**
     * 说明：View赋值
     * @param v
     * @param resString
     */
    public static void setText(View v,int resString){
        if (v != null){
            if (v instanceof TextView){
                ((TextView)v).setText(resString);
            }else if (v instanceof Button){
                ((Button)v).setText(resString);
            }else if (v instanceof CheckBox){
                ((CheckBox)v).setText(resString);
            }else if (v instanceof EditText){
                ((EditText)v).setText(resString);
            }else if (v instanceof RadioButton){
                ((RadioButton)v).setText(resString);
            }
        }
    }

    /**
     * 说明：View赋值
     * @param v
     * @param format
     * @param obj
     */
    public static void setText(View v,String format,Object...obj){
        setText(v,String.format(format,obj));
    }

    /**
     * 说明：View赋值
     * @param v
     * @param resFormat
     * @param obj
     */
    public static void setText(View v,int resFormat,Object...obj){
        setText(v,String.format(UIUtils.getString(resFormat),obj));
    }

    /**
     * 说明：设置View为GONE
     * @param view
     */
    public static void GONE(View view){
        if (view != null){
            if (view.getVisibility() != View.GONE){
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 说明：设置View为VISIBLE
     * @param view
     */
    public static void VISIBLE(View view){
        if (view != null){
            if (view.getVisibility() != View.VISIBLE){
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 说明：设置View为INVISIBLE
     * @param view
     */
    public static void INVISIBLE(View view){
        if (view != null){
            if (view.getVisibility() != View.INVISIBLE){
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 说明：判断是否为隐藏
     * @param view
     * @return
     */
    public static boolean isHide(View view){
        boolean flag = false;
        if (view != null){
            flag = view.getVisibility() != View.VISIBLE;
        }
        return flag;
    }

    /**
     * 说明：判断是否显示
     * @param view
     * @return
     */
    public static boolean isShow(View view){
        return !isHide(view);
    }

}
