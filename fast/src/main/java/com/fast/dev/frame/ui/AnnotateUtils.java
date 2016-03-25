package com.fast.dev.frame.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 说明：注解工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:43
 * <p/>
 * 版本：verson 1.0
 */

public class AnnotateUtils {

    /**
     * 说明：setContentView调用
     * @param activity
     */
    public static boolean initContentView(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        ContentView bindView = clazz.getAnnotation(ContentView.class);
        boolean isBind = false;
        if (bindView != null){
            isBind = true;
            int layoutId = bindView.value();
            try {
                Method method = clazz.getMethod("setContentView",int.class);
                method.setAccessible(true);
                method.invoke(activity,layoutId);
            }catch (Exception e){
                e.printStackTrace();
                isBind = false;
            }
        }
        return isBind;
    }

    /**
     * @param currentClass
     *            当前类，一般为Activity或Fragment
     * @param sourceView
     *            待绑定控件的直接或间接父控件
     */
    private static void initViewId(Object currentClass,View sourceView){
        //通过反射获取到全部属性，反射的字段可能是一个类字段（静态）或是实例字段
        Field []fields = currentClass.getClass().getDeclaredFields();
        if (fields != null && fields.length >0) {
            for (Field field : fields) {
                //返回BindView类型的注解内容
                Bind bindView = field.getAnnotation(Bind.class);
                if (bindView != null) {
                    int viewId = bindView.id();
                    boolean click = bindView.click();
                    try {
                        field.setAccessible(true);
                        if (click) {
                            sourceView.findViewById(viewId).setOnClickListener((OnClickListener)currentClass);
                        }
                        field.set(currentClass, sourceView.findViewById(viewId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param currentClass
     *            当前类，一般为Activity或Fragment
     * @param sourceView
     *            待绑定控件的直接或间接父控件
     */
    public static void init(Object currentClass, View sourceView) {
        initViewId(currentClass,sourceView);
    }

    /**
     * 说明：必须在setContentView之后调用
     * @param activity
     */
    public static void init(Activity activity){
        init(activity, activity.getWindow().getDecorView());
    }

    /**
     * 说明：必须在setContentView之后调用
     * @param view
     */
    public static void init(View view){
        Context context = view.getContext();
        if (context instanceof Activity) {
            init((Activity) context);
        }else {
            throw new RuntimeException("View必须是Activity");
        }
    }

    /**
     * 说明：必须在setContentView之后调用
     * @param fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void init(Fragment fragment){
        init(fragment, fragment.getActivity().getWindow().getDecorView());
    }

}

