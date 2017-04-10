package com.weizhi.redmartcatalog.ui.productdetail;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDetailPresenter implements ProductDetailContract.ActionListener {

    private ProductDetailContract.View mView;

    public ProductDetailPresenter(@NonNull ProductDetailContract.View view){
        mView = view;
    }

    @Override
    public void onSaveToListClick(@NonNull Product product) {
        mView.showSavedToList(product);
    }

    @Override
    public void onAddToCartClick(@NonNull Product product) {
        mView.showAddedToCart(product);
    }
}
