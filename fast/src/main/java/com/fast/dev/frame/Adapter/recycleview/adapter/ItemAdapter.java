package com.fast.dev.frame.Adapter.recycleview.adapter;

import android.widget.Filter;

import com.fast.dev.frame.Adapter.recycleview.AbstractAdapter;
import com.fast.dev.frame.Adapter.recycleview.IItem;
import com.fast.dev.frame.Adapter.recycleview.IItemAdapter;
import com.fast.dev.frame.Adapter.recycleview.IExpandable;
import com.fast.dev.frame.Adapter.recycleview.utils.IdDistributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 说明：ItemAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 2:04
 * <p/>
 * 版本：verson 1.0
 */
public class ItemAdapter<Item extends IItem> extends AbstractAdapter<Item> implements IItemAdapter<Item> {

    private List<Item> mItems = new ArrayList<>();

    private boolean mUseIdDistributor = true;
    private ItemFilter mItemFilter = new ItemFilter();
    private Predicate<Item> mFilterPredicate;

    public ItemAdapter withUseIdDistributor(boolean useIdDistributor) {
        this.mUseIdDistributor = useIdDistributor;
        return this;
    }

    public ItemAdapter<Item> withFilterPredicate(Predicate<Item> filterPredicate) {
        this.mFilterPredicate = filterPredicate;
        return this;
    }

    public void filter(CharSequence constraint) {
        mItemFilter.filter(constraint);
    }

    @Override
    public <T> T setSubItems(IExpandable<T, Item> collspsible, List<Item> subItems) {
        if (mUseIdDistributor){
            IdDistributor.checkIds(subItems);
        }
        return collspsible.withSubItems(subItems);
    }

    @Override
    public void set(List<Item> items) {
        if (mUseIdDistributor){
            IdDistributor.checkIds(items);
        }
        mItems = items;
        mapPossibleType(mItems);
        getFastAdapter().notifyAdapterItemRangeChanged(getFastAdapter().getItemCount(getOrder()),getAdapterItemCount());
    }

    @Override
    public void add(Item... items) {
        if (items != null) {
            if (mUseIdDistributor) {
                IdDistributor.checkIds(items);
            }
            Collections.addAll(mItems, items);
            mapPossibleType(Arrays.asList(items));
            getFastAdapter().notifyAdapterItemRangeInserted(getFastAdapter().getItemCount(getOrder()), items.length);
        }
    }

    @Override
    public void add(List<Item> items) {
        if (items != null) {
            if (mUseIdDistributor) {
                IdDistributor.checkIds(items);
            }
            mItems.addAll(items);
            mapPossibleType(items);
            getFastAdapter().notifyAdapterItemRangeInserted(getFastAdapter().getItemCount(getOrder()), items.size());
        }
    }

    @Override
    public void add(int position, Item... items) {
        if (mUseIdDistributor) {
            IdDistributor.checkIds(items);
        }
        if (items != null) {
            mItems.addAll(position - getFastAdapter().getItemCount(getOrder()), Arrays.asList(items));
            mapPossibleType(Arrays.asList(items));
            getFastAdapter().notifyAdapterItemRangeInserted(position, items.length);
        }
    }

    @Override
    public void add(int position, List<Item> items) {
        if (mUseIdDistributor) {
            IdDistributor.checkIds(items);
        }
        if (items != null) {
            mItems.addAll(position - getFastAdapter().getItemCount(getOrder()), items);
            mapPossibleType(items);
            getFastAdapter().notifyAdapterItemRangeInserted(position, items.size());
        }
    }

    @Override
    public void set(int position, Item item) {
        if (mUseIdDistributor) {
            IdDistributor.checkId(item);
        }
        mItems.set(position - getFastAdapter().getItemCount(getOrder()), item);
        mapPossibleType(item);
        getFastAdapter().notifyAdapterItemChanged(position);
    }

    @Override
    public void add(Item item) {
        if (mUseIdDistributor) {
            IdDistributor.checkId(item);
        }
        mItems.add(item);
        mapPossibleType(item);
        getFastAdapter().notifyAdapterItemInserted(getFastAdapter().getItemCount(getOrder()) + mItems.size());
    }

    @Override
    public void add(int position, Item item) {
        if (mUseIdDistributor) {
            IdDistributor.checkId(item);
        }
        mItems.add(position - getFastAdapter().getItemCount(getOrder()), item);
        mapPossibleType(item);
        getFastAdapter().notifyAdapterItemInserted(position);
    }

    @Override
    public void remove(int position) {
        mItems.remove(position - getFastAdapter().getItemCount(getOrder()));
        getFastAdapter().notifyAdapterItemRemoved(position);
    }

    @Override
    public void removeRange(int position, int itemCount) {
        int length = mItems.size();
        int saveItemCount = Math.min(itemCount, length - position + getFastAdapter().getItemCount(getOrder()));

        for (int i = 0; i < saveItemCount; i++) {
            mItems.remove(position - getFastAdapter().getItemCount(getOrder()));
        }

        getFastAdapter().notifyAdapterItemRangeRemoved(position, saveItemCount);
    }

    @Override
    public void clear() {
        int count = mItems.size();
        mItems.clear();
        getFastAdapter().notifyAdapterItemRangeRemoved(getFastAdapter().getItemCount(getOrder()), count);
    }

    @Override
    public int getOrder() {
        return 500;
    }

    @Override
    public int getAdapterItemCount() {
        return mItems.size();
    }

    @Override
    public List<Item> getAdapterItems() {
        return mItems;
    }

    @Override
    public Item getAdapterItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getAdapterPosition(Item item) {
        for (int i = 0;i < mItems.size();i++){
            if (mItems.get(i).getIdentifier() == item.getIdentifier()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getGlobalPosition(int position) {
        return position + getFastAdapter().getItemCount(getOrder());
    }

    public class ItemFilter extends Filter {
        private List<Item> mOriginalItems;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (mOriginalItems == null) {
                mOriginalItems = new ArrayList<>(mItems);
            }

            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = mOriginalItems;
                results.count = mOriginalItems.size();
            } else {
                List<Item> filteredItems = new ArrayList<>();

                if (mFilterPredicate != null) {
                    for (Item item : mOriginalItems) {
                        if (!mFilterPredicate.filter(item, constraint)) {
                            filteredItems.add(item);
                        }
                    }
                } else {
                    filteredItems = mItems;
                }

                results.values = filteredItems;
                results.count = filteredItems.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            animateTo((List<Item>) results.values);
        }
    }

    public List<Item> animateTo(List<Item> models) {
        applyAndAnimateRemovals(mItems, models);
        applyAndAnimateAdditions(mItems, models);
        applyAndAnimateMovedItems(mItems, models);
        return mItems;
    }

    private void applyAndAnimateRemovals(List<Item> from, List<Item> newModels) {
        for (int i = from.size() - 1; i >= 0; i--) {
            final Item model = from.get(i);
            if (!newModels.contains(model)) {
                //our methods work only with the global position
                remove(i + getFastAdapter().getItemCount(getOrder()));
            }
        }
    }

    private void applyAndAnimateAdditions(List<Item> from, List<Item> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Item model = newModels.get(i);
            if (!from.contains(model)) {
                //our methods work only with the global position
                add(i + getFastAdapter().getItemCount(getOrder()), model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Item> from, List<Item> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Item model = newModels.get(toPosition);
            final int fromPosition = from.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                final Item m = from.remove(fromPosition);
                from.add(toPosition, m);
                getFastAdapter().notifyAdapterItemMoved(fromPosition, toPosition);
            }
        }
    }
}
