package com.fast.dev.frame.Adapter.recycleview;

import java.util.List;

/**
 * 说明：可展开Item
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/23 21:16
 * <p/>
 * 版本：verson 1.0
 */
public interface IExpandable<T,Item extends IItem> {

    /**
     * 说明：是否展开
     * @return
     */
    boolean isExpanded();

    /**
     * 说明：设置展开
     * @param expanded
     * @return
     */
    T withIsExpanded(boolean expanded);

    /**
     * 说明：设置subItems
     * @param subItems 展开item
     * @return
     */
    T withSubItems(List<Item> subItems);

    /**
     * 说明：获取展开item集合
     * @return
     */
    List<Item> getSubItems();
}
