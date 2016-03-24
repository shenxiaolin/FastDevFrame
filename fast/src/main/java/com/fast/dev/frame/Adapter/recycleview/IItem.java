package com.fast.dev.frame.Adapter.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 说明：定义Item
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/22 16:18
 * <p/>
 * 版本：verson 1.0
 */
public interface IItem<T,VH extends RecyclerView.ViewHolder> extends IIdentifyable<T>{

    /**
     * 说明：获取item的Tag
     * @return
     */
    Object getTag();

    /**
     * 说明：设置item的Tag
     * @param tag
     * @return
     */
    T withTag(Object tag);

    /**
     * 说明：获取item是否可用
     * @return
     */
    boolean isEnabled();

    /**
     * 说明：获取item是否选中
     * @return
     */
    boolean isSelected();

    /**
     * 说明：设置item选中
     * @param selected
     * @return
     */
    T withSetSelected(boolean selected);

    /**
     * 说明：获取item是否可选中
     * @return
     */
    boolean isSelectable();

    /**
     * 说明：设置item是否可选中
     * @param selectable
     * @return
     */
    T withSelectable(boolean selectable);

    /**
     * 说明：获取item类型
     * @return
     */
    int getType();

    /**
     * 说明：获取item的视图
     * @return
     */
    int getLayoutRes();

    /**
     * 说明：生成默认view
     * @param ctx
     * @return
     */
    View generateView(Context ctx);

    /**
     * 说明：生成默认view
     * @param ctx
     * @param parent
     * @return
     */
    View generateView(Context ctx, ViewGroup parent);

    /**
     * 说明：获取item的ViewHolder
     * @param parent
     * @return
     */
    RecyclerView.ViewHolder getViewHolder(ViewGroup parent);

    /**
     * 说明：绑定item数据
     * @param holder
     */
    void bindView(VH holder);

    /**
     * 说明：通过id比较是否是相同item
     * @param id
     * @return
     */
    boolean equals(Integer id);

    /**
     * 说明：通过object比较是否是相同item
     * @param o
     * @return
     */
    boolean equals(Object o);
}
