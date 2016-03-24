package com.fast.dev.frame.Adapter.recycleview;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 说明：FastAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/22 16:57
 * <p/>
 * 版本：verson 1.0
 */
public class FastAdapter<Item extends IItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final String BUNDLE_SELECTIONS = "bundle_selections";
    protected static final String BUNDLE_EXPANDED = "bundle_expanded";

    private OnClickListener<Item> mOnPreClickListener;
    private OnClickListener<Item> mOnClickListener;
    private OnLongClickListener<Item> mOnPreLongClickListener;
    private OnLongClickListener<Item> mOnLongClickListener;
    private OnTouchListener<Item> mOnTouchListener;

    private boolean mSelectWithItemUpdate = false;
    private boolean mMultiSelect = false;
    private boolean mMultiSelectOnLongClick = true;
    private boolean mAllowDeselection = true;

    //记录条目选中状态
    private SortedSet<Integer> mSelections = new TreeSet<>();
    private SparseIntArray mExpanded = new SparseIntArray();

    //记录所有的Adapter
    private ArrayMap<Integer,IAdapter<Item>> mAdapters = new ArrayMap<>();
    //记录所有的Item
    private ArrayMap<Integer,Item> mTypeInstances = new ArrayMap<>();

    private OnCreateViewHolderListener mOnCreateViewHolderListener = new OnCreateViewHolderListenerImpl();
    private OnBindViewHolderListener mOnBindViewHolderListener = new OnBindViewHolderListenerImpl();

    /**
     * 说明：默认构造器
     */
    public FastAdapter(){
        setHasStableIds(true);
    }

    /**
     * 说明：定义一个Item的点击事件
     * @param onClickListener
     * @return
     */
    public FastAdapter<Item> withOnClickListener(OnClickListener<Item> onClickListener){
        this.mOnClickListener = onClickListener;
        return this;
    }

    /**
     * 说明：定义一个Item的点击事件执行之前执行
     * @param onClickListener
     * @return
     */
    public FastAdapter<Item> withOnPreClickListener(OnClickListener<Item> onClickListener){
        this.mOnPreClickListener = onClickListener;
        return this;
    }

    /**
     * 说明：定义一个Item的长按点击事件执行之前执行
     * @param onLongClickListener
     * @return
     */
    public FastAdapter<Item> withOnPreLongClickListener(OnLongClickListener<Item> onLongClickListener){
        this.mOnPreLongClickListener = onLongClickListener;
        return this;
    }

    /**
     * 说明：定义一个Item的长按点击事件
     * @param onLongClickListener
     * @return
     */
    public FastAdapter<Item> withOnLongClickListener(OnLongClickListener<Item> onLongClickListener){
        this.mOnLongClickListener = onLongClickListener;
        return this;
    }

    /**
     * 说明：定义一个Item的触摸事件
     * @param onTouchListener
     * @return
     */
    public FastAdapter<Item> withOnTouchListener(OnTouchListener<Item> onTouchListener){
        this.mOnTouchListener = onTouchListener;
        return this;
    }

    public FastAdapter<Item> withSelectWithItemUpdate(boolean selectWithItemUpdate){
        this.mSelectWithItemUpdate = selectWithItemUpdate;
        return this;
    }

    /**
     * 说明：是否开启多选
     * @param multiSelect
     * @return
     */
    public FastAdapter<Item> withMultiSelect(boolean multiSelect){
        this.mMultiSelect = multiSelect;
        return this;
    }

    /**
     * 说明：多Item长按事件是否开启
     * @param multiSelectOnLongClick
     * @return
     */
    public FastAdapter<Item> withMultiSelectOnLongClick(boolean multiSelectOnLongClick){
        this.mMultiSelectOnLongClick = multiSelectOnLongClick;
        return this;
    }

    public FastAdapter<Item> withAllowDeselection(boolean allowDeselection){
        this.mAllowDeselection = allowDeselection;
        return this;
    }

    public FastAdapter<Item> withSavedInstanceState(Bundle savedInstanceState) {
        return withSavedInstanceState(savedInstanceState, "");
    }

    public FastAdapter<Item> withSavedInstanceState(Bundle savedInstanceState,String prefix){
        if (savedInstanceState != null){
            //确保移除选择item
            deselect();
            int[] expandedItems = savedInstanceState.getIntArray(BUNDLE_EXPANDED+prefix);
            if (expandedItems != null){
                for (Integer expandedItem:expandedItems){
                    expand(expandedItem);
                }
            }
            int[] selections = savedInstanceState.getIntArray(BUNDLE_SELECTIONS+prefix);
            if (selections != null){
                for (Integer selection:selections){
                    select(selection);
                }
            }
        }
        return this;
    }

    public <A extends AbstractAdapter<Item>> void registerAdapter(A adapter){
        if (!mAdapters.containsKey(adapter.getOrder())){
            mAdapters.put(adapter.getOrder(),adapter);
        }
    }

    public void registerTypeInstance(Item item) {
        if (!mTypeInstances.containsKey(item.getType())) {
            mTypeInstances.put(item.getType(), item);
        }
    }

    public Map<Integer, Item> getTypeInstances() {
        return mTypeInstances;
    }

    public void select(Iterable<Integer> positions){
        for (Integer position:positions){
            select(position);
        }
    }

    public List<Item> deleteAllSelectedItems() {
        List<Item> deletedItems = new LinkedList<>();
        Set<Integer> selections = getSelections();
        while (selections.size() > 0) {
            Iterator<Integer> iterator = selections.iterator();
            int position = iterator.next();
            IAdapter adapter = getAdapter(position);
            if (adapter != null && adapter instanceof IItemAdapter) {
                deletedItems.add(getItem(position));
                ((IItemAdapter) adapter).remove(position);
            } else {
                iterator.remove();
            }
            selections = getSelections();
        }
        return deletedItems;
    }

    public int[] getExpandedItems() {
        int[] expandedItems = new int[mExpanded.size()];
        int length = mExpanded.size();
        for (int i = 0; i < length; i++) {
            expandedItems[i] = mExpanded.keyAt(i);
        }
        return expandedItems;
    }

    public void toggleExpandable(int position) {
        if (mExpanded.indexOfKey(position) >= 0) {
            collapse(position);
        } else {
            expand(position);
        }
    }

    public void collapse(int position) {
        Item item = getItem(position);
        if (item != null && item instanceof IExpandable) {
            IExpandable expandable = (IExpandable) item;

            if (expandable.isExpanded() && expandable.getSubItems() != null && expandable.getSubItems().size() > 0) {
                int totalAddedItems = expandable.getSubItems().size();

                int length = mExpanded.size();
                for (int i = 0; i < length; i++) {
                    if (mExpanded.keyAt(i) > position && mExpanded.keyAt(i) <= position + totalAddedItems) {
                        totalAddedItems = totalAddedItems + mExpanded.get(mExpanded.keyAt(i));
                    }
                }

                for (Integer value : mSelections) {
                    if (value > position && value <= position + totalAddedItems) {
                        deselect(value);
                    }
                }

                for (int i = length - 1; i >= 0; i--) {
                    if (mExpanded.keyAt(i) > position && mExpanded.keyAt(i) <= position + totalAddedItems) {
                        totalAddedItems = totalAddedItems - mExpanded.get(mExpanded.keyAt(i));

                        internalCollapse(mExpanded.keyAt(i));
                    }
                }

                internalCollapse(expandable, position);
            }
        }
    }

    private void internalCollapse(int position) {
        Item item = getItem(position);
        if (item != null && item instanceof IExpandable) {
            IExpandable expandable = (IExpandable) item;
            if (expandable.isExpanded() && expandable.getSubItems() != null && expandable.getSubItems().size() > 0) {
                internalCollapse(expandable, position);
            }
        }
    }

    private void internalCollapse(IExpandable expandable, int position) {
        IAdapter adapter = getAdapter(position);
        if (adapter != null && adapter instanceof IItemAdapter) {
            ((IItemAdapter) adapter).removeRange(position + 1, expandable.getSubItems().size());
        }

        expandable.withIsExpanded(false);
        int indexOfKey = mExpanded.indexOfKey(position);
        if (indexOfKey >= 0) {
            mExpanded.removeAt(indexOfKey);
        }
    }

    public void select(int position){
        select(position, false);
    }

    public void select(int position,boolean fireEvent){
        Item item = getItem(position);
        if (item != null) {
            item.withSetSelected(true);
            mSelections.add(position);
        }
        notifyItemChanged(position);

        if (mOnClickListener != null && fireEvent) {
            mOnClickListener.onClick(null, getAdapter(position), item, position);
        }
    }

    public void expand(int position){
        Item item = getItem(position);
        if (item != null && item instanceof IExpandable){
            IExpandable<?,Item> expandable = (IExpandable<?,Item>)item;
            if (!expandable.isExpanded() && expandable.getSubItems() != null && expandable.getSubItems().size() > 0){
                IAdapter<Item> adapter = getAdapter(position);
                if (adapter != null && adapter instanceof IItemAdapter){
                    ((IItemAdapter<Item>) adapter).add(position + 1, expandable.getSubItems());
                }
                expandable.withIsExpanded(true);
                mExpanded.put(position,expandable.getSubItems() != null ? expandable.getSubItems().size():0);
            }
        }
    }

    public void notifyAdapterItemChanged(int position){
        notifyAdapterItemChanged(position,null);
    }

    public void notifyAdapterItemChanged(int position, Object payload) {
        Item updateItem = getItem(position);
        if (updateItem.isSelected()) {
            mSelections.add(position);
        } else if (mSelections.contains(position)) {
            mSelections.remove(position);
        }

        if (payload == null) {
            notifyItemChanged(position);
        } else {
            notifyItemChanged(position, payload);
        }
    }

    public void notifyAdapterItemInserted(int position) {
        mSelections = AdapterUtils.adjustPosition(mSelections, position, Integer.MAX_VALUE, 1);
        mExpanded = AdapterUtils.adjustPosition(mExpanded, position, Integer.MAX_VALUE, 1);
        notifyItemInserted(position);
    }

    public void notifyAdapterItemRangeInserted(int position, int itemCount) {
        mSelections = AdapterUtils.adjustPosition(mSelections, position, Integer.MAX_VALUE, itemCount);
        mExpanded = AdapterUtils.adjustPosition(mExpanded, position, Integer.MAX_VALUE, itemCount);
        notifyItemRangeInserted(position, itemCount);
    }

    public void notifyAdapterItemRemoved(int position) {
        mSelections = AdapterUtils.adjustPosition(mSelections, position, Integer.MAX_VALUE, -1);
        mExpanded = AdapterUtils.adjustPosition(mExpanded, position, Integer.MAX_VALUE, -1);
        notifyItemRemoved(position);
    }

    public void notifyAdapterItemRangeRemoved(int position, int itemCount) {
        mSelections = AdapterUtils.adjustPosition(mSelections, position, Integer.MAX_VALUE, itemCount * (-1));
        mExpanded = AdapterUtils.adjustPosition(mExpanded, position, Integer.MAX_VALUE, itemCount * (-1));
        notifyItemRangeRemoved(position, itemCount);
    }

    /**
     * wraps notifyItemMoved
     *
     * @param fromPosition the global fromPosition
     * @param toPosition   the global toPosition
     */
    public void notifyAdapterItemMoved(int fromPosition, int toPosition) {
        collapse(fromPosition);
        collapse(toPosition);

        if (!mSelections.contains(fromPosition) && mSelections.contains(toPosition)) {
            mSelections.remove(toPosition);
            mSelections.add(fromPosition);
        } else if (mSelections.contains(fromPosition) && !mSelections.contains(toPosition)) {
            mSelections.remove(fromPosition);
            mSelections.add(toPosition);
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void notifyAdapterItemRangeChanged(int position, int itemCount) {
        notifyAdapterItemRangeChanged(position, itemCount, null);
    }

    public void notifyAdapterItemRangeChanged(int position, int itemCount, Object payload) {
        for (int i = position; i < position + itemCount; i++) {
            Item updateItem = getItem(position);
            if (updateItem.isSelected()) {
                mSelections.add(position);
            } else if (mSelections.contains(position)) {
                mSelections.remove(position);
            }
        }

        if (payload == null) {
            notifyItemRangeChanged(position, itemCount);
        } else {
            notifyItemRangeChanged(position, itemCount, payload);
        }
    }


    /**
     * 说明：移除未选中
     */
    public void deselect(){
        deselect(mSelections);
    }

    /**
     * 说明：移除未选中
     */
    public void deselect(int position){
        deselect(position,null);
    }

    /**
     * 说明：移除未选中
     * @param positions
     */
    public void deselect(Iterable<Integer> positions){
        Iterator<Integer> iterator = positions.iterator();
        while (iterator.hasNext()){
            deselect(iterator.next(),iterator);
        }
    }

    private void deselect(int position,Iterator<Integer> iterator){
        Item item = getItem(position);
        if (item != null){
            item.withSetSelected(false);
        }
        if (iterator == null){
            if (mSelections.contains(position)){
                mSelections.remove(position);
            }
        }else {
            iterator.remove();
        }
        notifyItemChanged(position);
    }

    /**
     * 说明：根据索引获取Item
     * @param position
     * @return
     */
    public Item getItem(int position){
        return getRelativeInfo(position).item;
    }

    public RelativeInfo<Item> getRelativeInfo(int position){
        if (position < 0){
            return new RelativeInfo<>();
        }
        RelativeInfo<Item> relativeInfo = new RelativeInfo<>();
        IAdapter<Item> adapter = getAdapter(position);
        if (adapter != null){
            relativeInfo.item = adapter.getAdapterItem(position-getItemCount(adapter.getOrder()));
            relativeInfo.adapter = adapter;
        }
        return relativeInfo;
    }

    /**
     * 说明：根据顺序获取数量
     * @param order
     * @return
     */
    public int getItemCount(int order){
        int size = 0;
        int len = mAdapters.size();
        for (int i = 0;i < len;i++){
            IAdapter adapter = mAdapters.valueAt(i);
            if (adapter.getOrder() < 0){
                continue;
            }
            if (adapter.getOrder() < order){
                size = adapter.getAdapterItemCount();
            }else {
                return size;
            }
        }
        return size;
    }

    /**
     * 说明：获取Adapter
     * @param position
     * @return
     */
    public IAdapter<Item> getAdapter(int position){
        int currentCount = 0;
        int len = mAdapters.size();
        for (int i = 0;i < len;i++){
            IAdapter<Item> adapter = mAdapters.valueAt(i);
            if (adapter.getOrder() < 0){
                continue;
            }
            if (currentCount <= position && currentCount + adapter.getAdapterItemCount() > position){
                return adapter;
            }
            currentCount = currentCount + adapter.getAdapterItemCount();
        }
        return null;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = mOnCreateViewHolderListener.onPreCreateViewHolder(parent,viewType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    boolean consumed = false;
                    RelativeInfo<Item> relativeInfo = getRelativeInfo(pos);
                    if (relativeInfo.item != null && relativeInfo.item.isEnabled()){
                        if (mOnPreClickListener != null){
                            consumed = mOnPreClickListener.onClick(v,relativeInfo.adapter,relativeInfo.item,pos);
                        }
                        if (!consumed && (!(mMultiSelect && mMultiSelectOnLongClick)) || !mMultiSelect){
                            handleSelection(v,relativeInfo.item,pos);
                        }
                        if (mOnClickListener != null){
                            mOnClickListener.onClick(v,relativeInfo.adapter,relativeInfo.item,pos);
                        }
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    boolean consumed = false;
                    RelativeInfo<Item> relativeInfo = getRelativeInfo(pos);
                    if (relativeInfo.item != null && relativeInfo.item.isEnabled()) {
                        if (mOnPreLongClickListener != null) {
                            consumed = mOnPreLongClickListener.onLongClick(v, relativeInfo.adapter, relativeInfo.item, pos);
                        }

                        if (!consumed && (mMultiSelect && mMultiSelectOnLongClick)) {
                            handleSelection(v, relativeInfo.item, pos);
                        }

                        if (mOnLongClickListener != null) {
                            consumed = mOnLongClickListener.onLongClick(v, relativeInfo.adapter, relativeInfo.item, pos);
                        }
                    }
                    return consumed;
                }
                return false;
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mOnTouchListener != null){
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        RelativeInfo<Item> relativeInfo = getRelativeInfo(pos);
                        return mOnTouchListener.onTouch(v,event,relativeInfo.adapter,relativeInfo.item,pos);
                    }
                }
                return false;
            }
        });
        return mOnCreateViewHolderListener.onPostCreateViewHolder(holder);
    }

    private void handleSelection(View view,Item item,int position){
        if (!item.isSelectable()){
            return;
        }
        if (item.isSelected() && mAllowDeselection){
            return;
        }
        boolean selected = mSelections.contains(position);
        if (mSelectWithItemUpdate || view == null){
            if (!mMultiSelect){
                deselect();
            }
            if (selected){
                deselect(position);
            }else {
                select(position);
            }
        }else {
            if (!mMultiSelect){
                Iterator<Integer> iterator = mSelections.iterator();
                while (iterator.hasNext()){
                    Integer pos = iterator.next();
                    if (pos != position){
                        deselect(pos,iterator);
                    }
                }
            }
            item.withSetSelected(!selected);
            view.setSelected(!selected);
            if (selected){
                if (mSelections.contains(position)){
                    mSelections.remove(position);
                }
            }else {
                mSelections.add(position);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mOnBindViewHolderListener.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        int len = mAdapters.size();
        for (int i = 0;i < len;i++){
            IAdapter adapter = mAdapters.valueAt(i);
            if (adapter.getOrder() < 0){
                continue;
            }
            size=size+adapter.getAdapterItemCount();
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIdentifier();
    }

    public Set<Integer> getSelections(){
        return mSelections;
    }

    public Set<Item> getSelectedItems(){
        Set<Item> items = new HashSet<>();
        for (Integer position : getSelections()){
            items.add(getItem(position));
        }
        return items;
    }

    public void toggleSelection(int position) {
        if (mSelections.contains(position)) {
            deselect(position);
        } else {
            select(position);
        }
    }

    public int getPosition(Item item){
        if (item.getIdentifier() == -1){
            return -1;
        }
        int position = 0;
        int len = mAdapters.size();
        for (int i = 0;i < len;i++){
            IAdapter<Item> adapter = mAdapters.valueAt(i);
            if (adapter.getOrder() < 0){
                continue;
            }
            int relativePostion = adapter.getAdapterPosition(item);
            if (relativePostion != -1){
                return position + relativePostion;
            }
            position = adapter.getAdapterItemCount();
        }
        return -1;
    }

    /**
     * 说明：item点击事件
     * @param <Item>
     */
    public interface OnClickListener<Item extends IItem>{
        boolean onClick(View view, IAdapter<Item> adapter, Item item, int position);
    }

    /**
     * 说明：item长按点击事件
     * @param <Item>
     */
    public interface OnLongClickListener<Item extends IItem>{
        boolean onLongClick(View view, IAdapter<Item> adapter, Item item, int position);
    }

    /**
     * 说明：item触摸事件
     * @param <Item>
     */
    public interface OnTouchListener<Item extends IItem>{
        boolean onTouch(View view, MotionEvent event, IAdapter<Item> adapter, Item item, int position);
    }

    /**
     * 说明：一个相对位置的Item信息
     * @param <Item>
     */
    public static class RelativeInfo<Item extends IItem>{
        public IAdapter<Item> adapter = null;
        public Item item = null;
    }

    /**
     * 说明：创建ViewHolder监听器
     */
    public interface OnCreateViewHolderListener{
        RecyclerView.ViewHolder onPreCreateViewHolder(ViewGroup parenet, int viewType);
        RecyclerView.ViewHolder onPostCreateViewHolder(RecyclerView.ViewHolder viewHolder);
    }

    /**
     * 说明：OnCreateViewHolderListener实现
     */
    public class OnCreateViewHolderListenerImpl implements OnCreateViewHolderListener{

        @Override
        public RecyclerView.ViewHolder onPreCreateViewHolder(ViewGroup parenet, int viewType) {
            return mTypeInstances.get(viewType).getViewHolder(parenet);
        }

        @Override
        public RecyclerView.ViewHolder onPostCreateViewHolder(RecyclerView.ViewHolder viewHolder) {
            return viewHolder;
        }
    }

    /**
     * 说明：bindViewHolder监听
     */
    public interface OnBindViewHolderListener{
        void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);
    }

    /**
     * 说明：bindViewHolder实现
     */
    public class OnBindViewHolderListenerImpl implements OnBindViewHolderListener{

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            getItem(position).bindView(viewHolder);
        }
    }

}
