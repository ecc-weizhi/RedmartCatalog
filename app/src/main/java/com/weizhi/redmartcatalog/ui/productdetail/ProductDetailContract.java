package com.weizhi.redmartcatalog.ui.productdetail;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface ProductDetailContract {
    interface View{
        void showAddToCart(int quantityInCart);
        void showSavedToList(@NonNull Product product);
    }

    interface ActionListener{
        void onSaveToListClick(@NonNull Product product);
        void onAddToCartClick(@NonNull Product product);
        void onMinusClick(@NonNull Product product);
        void onPlusClick(@NonNull Product product);
    }
}
