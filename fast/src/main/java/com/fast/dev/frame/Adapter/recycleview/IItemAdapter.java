package com.fast.dev.frame.Adapter.recycleview;

import java.util.List;

/**
 * 说明：ItemAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/23 21:36
 * <p/>
 * 版本：verson 1.0
 */
public interface IItemAdapter<Item extends IItem> extends IAdapter<Item> {

    /**
     * 说明：设置展开items数据
     * @param collspsible
     * @param subItems
     * @param <T>
     * @return
     */
    <T> T setSubItems(IExpandable<T, Item> collspsible, List<Item> subItems);

    /**
     * 说明：设置新的数据
     * @param items
     */
    void set(List<Item> items);

    /**
     * 说明：添加一条数据，或者多条数据
     * @param items
     */
    void add(Item... items);

    /**
     * 说明：添加数据集合
     * @param items
     */
    void add(List<Item> items);

    /**
     * 说明：指定位置添加一条，或者多条数据
     * @param position
     * @param items
     */
    void add(int position, Item... items);

    /**
     * 说明：指定位置添加数据集合
     * @param position
     * @param items
     */
    void add(int position, List<Item> items);

    void set(int position, Item item);

    void add(Item item);

    void add(int position, Item item);

    void remove(int position);

    void removeRange(int position, int itemCount);

    void clear();

    /**
     * 说明：过滤数据
     */
    interface Predicate<Item extends IItem> {
        /**
         * @param item
         * @param constraint
         * @return true过滤，false保留
         */
        boolean filter(Item item, CharSequence constraint);
    }
}
