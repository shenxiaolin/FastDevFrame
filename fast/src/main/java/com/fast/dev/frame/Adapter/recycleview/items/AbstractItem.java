package com.fast.dev.frame.Adapter.recycleview.items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fast.dev.frame.Adapter.recycleview.IItem;
import com.fast.dev.frame.Adapter.recycleview.ViewHolderFactory;

import java.lang.reflect.ParameterizedType;

/**
 * 说明：AbstractItem
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 1:05
 * <p/>
 * 版本：verson 1.0
 */
public abstract class AbstractItem<Item extends IItem,VH extends RecyclerView.ViewHolder> implements IItem<Item,VH> {

    protected long mIdentifier = -1;
    protected Object mTag;
    protected boolean mEnabled = true;
    protected boolean mSelected = false;
    protected boolean mSelectable = true;


    @Override
    public Object getTag() {
        return mTag;
    }

    @Override
    public Item withTag(Object tag) {
        this.mTag = tag;
        return (Item)this;
    }

    @Override
    public boolean isEnabled() {
        return mEnabled;
    }

    public Item withEnable(boolean enable){
        this.mEnabled = enable;
        return (Item)this;
    }

    @Override
    public boolean isSelected() {
        return mSelected;
    }

    @Override
    public Item withSetSelected(boolean selected) {
        this.mSelected = selected;
        return (Item) this;
    }

    @Override
    public boolean isSelectable() {
        return mSelectable;
    }

    @Override
    public Item withSelectable(boolean selectable) {
        this.mSelectable = selectable;
        return (Item) this;
    }

    @Override
    public View generateView(Context ctx) {
        RecyclerView.ViewHolder viewHolder = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), null, false));
        bindView((VH)viewHolder);
        return viewHolder.itemView;
    }

    public VH getViewHolder(View view){
        ViewHolderFactory viewHolderFactory = getFactory();
        if (viewHolderFactory == null){
            try {
                return (VH) ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]).getDeclaredConstructor(View.class).newInstance(view);
            } catch (Exception e) {
                throw new RuntimeException("something really bad happened. if this happens more often, head over to GitHub and read how to switch to the ViewHolderFactory");
            }
        }else {
            return (VH) viewHolderFactory.create(view);
        }
    }

    public abstract ViewHolderFactory getFactory();

    @Override
    public View generateView(Context ctx, ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        return getViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(),parent,false));
    }

    @Override
    public void bindView(VH holder) {
        holder.itemView.setSelected(isSelected());
        holder.itemView.setTag(this);
    }

    @Override
    public boolean equals(Integer id) {
        return id != null && id == mIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractItem<?,?> that = (AbstractItem<?,?>)o;
        return mIdentifier == this.mIdentifier;
    }

    @Override
    public Item withIdentifier(long identifier) {
        this.mIdentifier = identifier;
        return (Item)this;
    }

    @Override
    public long getIdentifier() {
        return mIdentifier;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(mIdentifier).hashCode();
    }
}
