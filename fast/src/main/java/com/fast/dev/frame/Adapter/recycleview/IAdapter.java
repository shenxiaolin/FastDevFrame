package com.fast.dev.frame.Adapter.recycleview;

import java.util.List;

/**
 * 说明：Adapter定义
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/23 20:07
 * <p/>
 * 版本：verson 1.0
 */
public interface IAdapter<Item extends IItem> {

    /**
     * 说明：获取FastAdapter
     * @return
     */
    FastAdapter getFastAdapter();

    /**
     * 说明：定义在Adapter中的顺序
     * @return 返回Adapter中的顺序
     */
    int getOrder();

    /**
     * 说明：获取item数量
     * @return
     */
    int getAdapterItemCount();

    /**
     * 说明：获取Adapter中的数据
     * @return
     */
    List<Item> getAdapterItems();

    /**
     * 说明：获取Item数据
     * @param position
     * @return
     */
    Item getAdapterItem(int position);

    int getAdapterPosition(Item item);

    /**
     * 说明：获取全局索引
     * @param position
     * @return
     */
    int getGlobalPosition(int position);

    /**
     * 说明：获取全局Item数量
     * @return
     */
    int getItemCount();

    /**
     * 说明：获取全局Item数据
     * @param position 全局索引Position
     * @param <Item>
     * @return
     */
    <Item extends IItem> Item getItem(int position);
}
