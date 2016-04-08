package com.fast.dev.frame.Adapter.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.fast.dev.frame.utils.UIUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：BaseRecyclerAdapter
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 1:16
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<T> mData;
    private View mEmptyView;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter.OnItemClickListener mOnItemClickListener;
    private BaseRecyclerAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerAdapter(RecyclerView recyclerView,List<T> data){
        mData = data == null ? new ArrayList<T>() : data;
        this.mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(UIUtils.inflate(getItemLayoutId(viewType),parent,false));
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
        if (mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convert(holder,mData.get(position),position);
    }

    public abstract int getItemLayoutId(int viewType);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 说明：赋值
     * @param holder
     * @param item
     * @param position
     */
    public abstract void convert(RecyclerViewHolder holder,T item,int position);

    public void setEmptyView(View emptyView){
        mEmptyView = emptyView;
        updateEmptyStatus();
    }

    /**
     * 说明：刷新状态
     */
    private void updateEmptyStatus(){
        boolean isEmpty = getData() == null || getData().isEmpty() ? true : false;
        if (isEmpty){
            if (mEmptyView != null){
                mEmptyView.setVisibility(View.VISIBLE);
            }
            if (mRecyclerView != null){
                mRecyclerView.setVisibility(View.GONE);
            }
        }else {
            if (mEmptyView != null){
                mEmptyView.setVisibility(View.GONE);
            }
            if (mRecyclerView != null){
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 说明：添加数据
     * @param position
     * @param item
     */
    public void add(int position,T item){
        if (item != null && position >=0 && position < mData.size()){
            mData.add(position, item);
            notifyItemChanged(position);
            updateEmptyStatus();
        }
    }

    /**
     * 说明：添加数据
     * @param position
     * @param data
     */
    public void addAll(int position,List<T> data){
        if (data == null || data.isEmpty()){
            return;
        }
        if (position >=0 && position < mData.size()){
            mData.addAll(position, data);
            notifyDataSetChanged();
            updateEmptyStatus();
        }
    }

    /**
     * 说明：删除数据
     * @param position
     */
    public void remove(int position){
        if (position >= 0 && position < mData.size()){
            mData.remove(position);
            notifyItemInserted(position);
            updateEmptyStatus();
        }
    }


    /**
     * 说明：刷新数据
     * @param data
     */
    public void refresh(List<T> data){
        if (data == null || data.isEmpty()){
            mData.clear();
        }else {
            mData = data;
        }
        notifyDataSetChanged();
        updateEmptyStatus();
    }

    /**
     * 说明：获取数据
     * @return
     */
    public List<T> getData(){
        return mData;
    }

    /**
     * 说明：设置点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    /**
     * 说明：设置点击事件
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    /**
     * 说明：点击
     */
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    /**
     * 说明：长按
     */
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
}
