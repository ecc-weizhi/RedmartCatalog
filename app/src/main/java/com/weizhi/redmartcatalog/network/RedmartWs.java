package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface RedmartWs {
    @NonNull
    RestResponse<List<Product>> search(int page, int pageSize);

    @NonNull
    RestResponse<Product> getProduct(long productId);
}
