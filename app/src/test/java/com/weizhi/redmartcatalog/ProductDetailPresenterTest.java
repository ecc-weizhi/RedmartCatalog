package com.weizhi.redmartcatalog;

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
        // Simple test to verify that when onAddToCartClick() happen, the view will display
        // whatever is supposed to be display for adding product to cart.
        ProductDetailContract.View mockedView = Mockito.mock(ProductDetailContract.View.class);
        Product mockedProduct = Mockito.mock(Product.class);

        ProductDetailPresenter presenter = new ProductDetailPresenter(mockedView);
        presenter.onAddToCartClick(mockedProduct);

        Mockito.verify(mockedView, Mockito.atLeastOnce()).showAddedToCart(mockedProduct);
    }

    @Test
    public void testOnSaveToListClick() throws Exception {
        // Simple test to verify that when onSaveToListClick() happen, the view will display
        // whatever is supposed to be display for saving product to list.
        ProductDetailContract.View mockedView = Mockito.mock(ProductDetailContract.View.class);
        Product mockedProduct = Mockito.mock(Product.class);

        ProductDetailPresenter presenter = new ProductDetailPresenter(mockedView);
        presenter.onSaveToListClick(mockedProduct);

        Mockito.verify(mockedView, Mockito.atLeastOnce()).showSavedToList(mockedProduct);
    }
}
