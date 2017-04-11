package com.weizhi.redmartcatalog.ui.productdetail;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Cart;
import com.weizhi.redmartcatalog.model.Product;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDetailPresenter implements ProductDetailContract.ActionListener {

    private ProductDetailContract.View mView;
    private Cart mCart;

    public ProductDetailPresenter(@NonNull ProductDetailContract.View view,
                                  @NonNull Cart cart){
        mView = view;
        mCart = cart;
    }

    @Override
    public void onSaveToListClick(@NonNull Product product) {
        mView.showSavedToList(product);
    }

    @Override
    public void onAddToCartClick(@NonNull Product product) {
        onPlusClick(product);
    }

    @Override
    public void onMinusClick(@NonNull Product product) {
        mCart.removeFromCart(product.getId(), 1);
        int quantity = mCart.getQuantity(product.getId());
        mView.showAddToCart(quantity);
    }

    @Override
    public void onPlusClick(@NonNull Product product) {
        mCart.addToCart(product.getId(), 1);
        int quantity = mCart.getQuantity(product.getId());
        mView.showAddToCart(quantity);
    }
}
