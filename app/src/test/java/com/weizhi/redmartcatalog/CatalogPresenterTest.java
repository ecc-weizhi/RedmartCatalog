package com.weizhi.redmartcatalog;

import com.birbit.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.ui.catalog.CatalogContract;
import com.weizhi.redmartcatalog.ui.catalog.CatalogPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogPresenterTest {
    @Test
    public void testOnAddToCartClick() throws Exception {
        // Simple test to verify that when onAddToCartClick() happen, the view will display
        // whatever is supposed to be display for adding product to cart.
        CatalogContract.View mockedView = Mockito.mock(CatalogContract.View.class);
        JobManager mockedJobManager = Mockito.mock(JobManager.class);
        Bus mockedBus = Mockito.mock(Bus.class);
        Product mockedProduct = Mockito.mock(Product.class);

        CatalogPresenter presenter = new CatalogPresenter(mockedView, mockedJobManager, mockedBus);
        presenter.onAddToCartClick(mockedProduct);

        Mockito.verify(mockedView, Mockito.atLeastOnce()).showAddedToCart(mockedProduct);
    }
}
