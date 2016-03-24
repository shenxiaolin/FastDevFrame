package com.fast.dev.frame.Adapter.recycleview.stickyheader;

import android.support.v7.widget.RecyclerView;
/**
 * 说明：OrientationProvider
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 0:46
 * <p/>
 * 版本：verson 1.0
 */
public interface OrientationProvider {

  public int getOrientation(RecyclerView recyclerView);

  public boolean isReverseLayout(RecyclerView recyclerView);
}
