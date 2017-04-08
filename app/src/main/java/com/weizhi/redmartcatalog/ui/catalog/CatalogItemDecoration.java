package com.weizhi.redmartcatalog.ui.catalog;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpacePx;
    private int mSpanCount;

    public CatalogItemDecoration(int spacePx, int spanCount) {
        mSpacePx = spacePx;
        mSpanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        // Top margin
        outRect.top = position < mSpanCount ? mSpacePx : 0;
        outRect.left = position % 3 == 0 ? mSpacePx : 0;
        outRect.right = mSpacePx;
        outRect.bottom = mSpacePx;
    }
}
