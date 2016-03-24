package com.fast.dev.frame.Adapter.recycleview.stickyheader;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 说明：HeaderProvider
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/24 0:46
 * <p/>
 * 版本：verson 1.0
 */
public interface HeaderProvider {

  public View getHeader(RecyclerView recyclerView, int position);

  void invalidate();
}
