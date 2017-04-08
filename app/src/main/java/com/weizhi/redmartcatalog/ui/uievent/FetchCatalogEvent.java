package com.weizhi.redmartcatalog.ui.uievent;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class FetchCatalogEvent {
    public final boolean isSucceed;
    public final List<Product> productList;
    public final int page;
    public final int pageSize;

    public FetchCatalogEvent(boolean isSucceed,
                             @NonNull List<Product> productList,
                             int page,
                             int pageSize) {
        this.isSucceed = isSucceed;
        this.productList = productList;
        this.page = page;
        this.pageSize = pageSize;
    }
}
