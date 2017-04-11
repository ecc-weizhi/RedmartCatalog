package com.weizhi.redmartcatalog;

import com.birbit.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.weizhi.redmartcatalog.model.Cart;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.ui.catalog.CatalogContract;
import com.weizhi.redmartcatalog.ui.catalog.CatalogPresenter;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogPresenterTest {
    @Test
    public void testOnAddToCartClick() throws Exception {
        // Simple test to verify that when onAddToCartClick() happen, we will call the method to
        // add product to cart and tell the view to display whatever is supposed to be display for
        // adding product to cart.
        CatalogContract.View mockedView = Mockito.mock(CatalogContract.View.class);
        JobManager mockedJobManager = Mockito.mock(JobManager.class);
        Bus mockedBus = Mockito.mock(Bus.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        CatalogPresenter presenter = new CatalogPresenter(mockedView, mockedJobManager, mockedBus, mockedCart);
        presenter.onAddToCartClick(mockedProduct);

        Mockito.verify(mockedCart, Mockito.times(1)).addToCart(mockedProduct.getId(), 1);
        Mockito.verify(mockedView, Mockito.atLeastOnce()).showCartQuantityChange(mockedProduct);
    }

    @Test
    public void testOnMinusClick() throws Exception {
        // Simple test to verify that when onMinusClick() happen, we will call the method to
        // remove product from cart and tell the view to display whatever is supposed to be display
        // for removing product from cart.
        CatalogContract.View mockedView = Mockito.mock(CatalogContract.View.class);
        JobManager mockedJobManager = Mockito.mock(JobManager.class);
        Bus mockedBus = Mockito.mock(Bus.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        CatalogPresenter presenter = new CatalogPresenter(mockedView, mockedJobManager, mockedBus, mockedCart);
        presenter.onMinusClick(mockedProduct);

        Mockito.verify(mockedCart, Mockito.times(1)).removeFromCart(mockedProduct.getId(), 1);
        Mockito.verify(mockedView, Mockito.atLeastOnce()).showCartQuantityChange(mockedProduct);
    }

    @Test
    public void testOnPlusClick() throws Exception {
        // Simple test to verify that when onPlusClick() happen, we will call the method to
        // add product to cart and tell the view to display whatever is supposed to be display for
        // adding product to cart.
        CatalogContract.View mockedView = Mockito.mock(CatalogContract.View.class);
        JobManager mockedJobManager = Mockito.mock(JobManager.class);
        Bus mockedBus = Mockito.mock(Bus.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        CatalogPresenter presenter = new CatalogPresenter(mockedView, mockedJobManager, mockedBus, mockedCart);
        presenter.onPlusClick(mockedProduct);

        Mockito.verify(mockedCart, Mockito.times(1)).addToCart(mockedProduct.getId(), 1);
        Mockito.verify(mockedView, Mockito.atLeastOnce()).showCartQuantityChange(mockedProduct);
    }

    @Test
    public void testOnProductClick() throws Exception {
        // Simple test to verify that when onProductClick() happen, we will tell the view to show
        // product detail.
        CatalogContract.View mockedView = Mockito.mock(CatalogContract.View.class);
        JobManager mockedJobManager = Mockito.mock(JobManager.class);
        Bus mockedBus = Mockito.mock(Bus.class);
        Product mockedProduct = Mockito.mock(Product.class);
        Cart mockedCart = Mockito.mock(Cart.class);

        CatalogPresenter presenter = new CatalogPresenter(mockedView, mockedJobManager, mockedBus, mockedCart);
        presenter.onProductClick(mockedProduct);

        Mockito.verify(mockedView, Mockito.times(1)).showGoToProductDetail(mockedProduct);
    }
}
