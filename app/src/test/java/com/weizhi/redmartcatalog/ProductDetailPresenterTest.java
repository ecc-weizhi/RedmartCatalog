package com.weizhi.redmartcatalog;

import com.weizhi.redmartcatalog.model.Cart;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.ui.productdetail.ProductDetailContract;
import com.weizhi.redmartcatalog.ui.productdetail.ProductDetailPresenter;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDetailPresenterTest {
    @Test
    public void testOnAddToCartClick() throws Exception {
        // Simple test to verify that when onAddToCartClick() happen, we will call the method to
        // add product to cart and tell the view to display whatever is supposed to be display for
        // adding product to cart.
        ProductDetailContract.View mockedView = Mockito.mock(ProductDetailContract.View.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        ProductDetailPresenter presenter = new ProductDetailPresenter(mockedView, mockedCart);
        presenter.onAddToCartClick(mockedProduct);

        Mockito.verify(mockedCart, Mockito.times(1)).addToCart(mockedProduct.getId(), 1);
        Mockito.verify(mockedView, Mockito.atLeastOnce())
                .showAddToCart(mockedCart.getQuantity(mockedProduct.getId()));
    }

    @Test
    public void testOnMinusClick() throws Exception {
        // Simple test to verify that when onMinusClick() happen, we will call the method to
        // remove product from cart and tell the view to display whatever is supposed to be display
        // for removing product from cart.
        ProductDetailContract.View mockedView = Mockito.mock(ProductDetailContract.View.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        ProductDetailPresenter presenter = new ProductDetailPresenter(mockedView, mockedCart);
        presenter.onMinusClick(mockedProduct);

        Mockito.verify(mockedCart, Mockito.times(1)).removeFromCart(mockedProduct.getId(), 1);
        Mockito.verify(mockedView, Mockito.atLeastOnce())
                .showAddToCart(mockedCart.getQuantity(mockedProduct.getId()));
    }

    @Test
    public void testOnPlusClick() throws Exception {
        // Simple test to verify that when onPlusClick() happen, we will call the method to
        // add product to cart and tell the view to display whatever is supposed to be display for
        // adding product to cart.
        ProductDetailContract.View mockedView = Mockito.mock(ProductDetailContract.View.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        ProductDetailPresenter presenter = new ProductDetailPresenter(mockedView, mockedCart);
        presenter.onPlusClick(mockedProduct);

        Mockito.verify(mockedCart, Mockito.times(1)).addToCart(mockedProduct.getId(), 1);
        Mockito.verify(mockedView, Mockito.atLeastOnce())
                .showAddToCart(mockedCart.getQuantity(mockedProduct.getId()));
    }

    @Test
    public void testOnSaveToListClick() throws Exception {
        // Simple test to verify that when onSaveToListClick() happen, the view will display
        // whatever is supposed to be display for saving product to list.
        ProductDetailContract.View mockedView = Mockito.mock(ProductDetailContract.View.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        ProductDetailPresenter presenter = new ProductDetailPresenter(mockedView, mockedCart);
        presenter.onSaveToListClick(mockedProduct);

        Mockito.verify(mockedView, Mockito.atLeastOnce()).showSavedToList(mockedProduct);
    }
}
