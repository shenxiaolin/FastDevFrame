package com.fast.dev.frame.Adapter.recycleview;

import android.util.SparseIntArray;

import com.fast.dev.frame.bean.I_Model;
import com.fast.dev.frame.Adapter.recycleview.items.BaseItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 说明：AdapterUtils
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 0:39
 * <p/>
 * 版本：verson 1.0
 */
public class AdapterUtils {

    public static SortedSet<Integer> adjustPosition(Set<Integer> positions, int startPosition, int endPosition, int adjustBy) {
        SortedSet<Integer> newPositions = new TreeSet<>();

        for (Integer entry : positions) {
            int position = entry;

            if (position < startPosition || position > endPosition) {
                newPositions.add(position);
            } else if (adjustBy > 0) {
                newPositions.add(position + adjustBy);
            } else if (adjustBy < 0) {
                if (position > startPosition + adjustBy && position <= startPosition) {
                } else {
                    newPositions.add(position + adjustBy);
                }
            }
        }

        return newPositions;
    }

    public static SparseIntArray adjustPosition(SparseIntArray positions, int startPosition, int endPosition, int adjustBy) {
        SparseIntArray newPositions = new SparseIntArray();

        for (int i = 0; i < positions.size(); i++) {
            int position = positions.keyAt(i);

            if (position < startPosition || position > endPosition) {
                newPositions.put(position, positions.valueAt(i));
            } else if (adjustBy > 0) {
                newPositions.put(position + adjustBy, positions.valueAt(i));
            } else if (adjustBy < 0) {
                if (position > startPosition + adjustBy && position <= startPosition) {
                } else {
                    newPositions.put(position + adjustBy, positions.valueAt(i));
                }
            }
        }

        return newPositions;
    }

    /**
     * 说明：包装数据
     */
    public static <BeanClazz extends I_Model,ItemClazz extends BaseItem> List<ItemClazz> warpData(ItemClazz itemClazz,List<BeanClazz> data,long startId){
        List<ItemClazz> itemLists = new ArrayList<>();
        if (data != null && !data.isEmpty()){
            long id = 1;
            for(BeanClazz bean:data){
                if (itemClazz instanceof BaseItem){
                    try {
                        Class clazz = Class.forName(itemClazz.getClass().getName());
                        ItemClazz item = (ItemClazz)clazz.newInstance();
                        item.setItem(bean);
                        item.withIdentifier(startId+(id++));
                        itemLists.add(item);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return itemLists;
    }
}
