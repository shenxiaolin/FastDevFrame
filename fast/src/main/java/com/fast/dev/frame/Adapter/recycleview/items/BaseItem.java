package com.fast.dev.frame.Adapter.recycleview.items;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fast.dev.frame.Adapter.recycleview.IItem;
import com.fast.dev.frame.Adapter.recycleview.ViewHolderFactory;

/**
 * 说明：RecyclerView的item集成BaseItem
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/25 16:59
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BaseItem<Item extends IItem,VH extends RecyclerView.ViewHolder,TData extends Object> extends AbstractItem<Item,VH>{

    private TData item;

    public BaseItem setItem(TData t){
        this.item = t;
        return this;
    }

    @Override
    public ViewHolderFactory getFactory() {
        return new ItemFactory();
    }

    @Override
    public int getType() {
        return 0;
    }

    public class ItemFactory implements ViewHolderFactory<VH> {

        @Override
        public VH create(View v) {
            return createViewHolder(v);
        }
    }

    /**
     * 说明：创建ViewHolder
     */
    public abstract VH createViewHolder(View v);

    @Override
    public void bindView(VH holder) {
        super.bindView(holder);
        convert(holder,item);
    }

    /**
     * 说明：绑定数据
     */
    public abstract void convert(VH holder,TData item);
}
