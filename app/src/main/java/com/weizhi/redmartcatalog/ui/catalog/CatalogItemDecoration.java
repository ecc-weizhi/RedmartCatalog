package com.weizhi.redmartcatalog.ui.catalog;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpacePx;

    public CatalogItemDecoration(int spacePx) {
        mSpacePx = spacePx;
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = mSpacePx;
        outRect.left = mSpacePx;
        outRect.right = mSpacePx;
        outRect.bottom = mSpacePx;
    }
}
