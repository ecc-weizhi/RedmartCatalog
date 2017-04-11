package com.weizhi.redmartcatalog.ui.catalog;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface CatalogContract {
    interface View{
        void addProductList(int page, int pageSize, List<Product> productList);
        void showCartQuantityChange(@NonNull Product product);
        void showGoToProductDetail(@NonNull Product product);
    }

    interface ActionListener{
        void onStart();
        void onStop();
        void fetchCatalog(int page, int pageSize);
        void onAddToCartClick(@NonNull Product product);
        void onProductClick(@NonNull Product product);
        void onMinusClick(@NonNull Product product);
        void onPlusClick(@NonNull Product product);
    }
}
