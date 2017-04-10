package com.weizhi.redmartcatalog.ui.productdetail;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface ProductDetailContract {
    interface View{
        void showAddedToCart(@NonNull Product product);
        void showSavedToList(@NonNull Product product);
    }

    interface ActionListener{
        void onSaveToListClick(@NonNull Product product);
        void onAddToCartClick(@NonNull Product product);
    }
}
