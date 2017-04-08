package com.weizhi.redmartcatalog.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    // The minimum number of items to have below your current scroll position
    // before mLoading more.
    private static final int VISIBLE_THRESHOLD = 6;

    private final LoadMoreCallback mCallback;
    private final RecyclerView.LayoutManager mLayoutManager;

    public LoadMoreScrollListener(@NonNull GridLayoutManager gridLayoutManager,
                                  @NonNull LoadMoreCallback callback) {
        mLayoutManager = gridLayoutManager;
        mCallback = callback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy){
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        if((lastVisibleItemPosition + VISIBLE_THRESHOLD) >= totalItemCount ){
            mCallback.tryLoadMore();
        }
    }

    public interface LoadMoreCallback{
        void tryLoadMore();
    }
}