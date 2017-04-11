package com.weizhi.redmartcatalog.ui.catalog;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.weizhi.redmartcatalog.model.Cart;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.service.job.FetchCatalogJob;
import com.weizhi.redmartcatalog.ui.uievent.FetchCatalogEvent;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogPresenter implements CatalogContract.ActionListener {

    private CatalogContract.View mView;
    private JobManager mJobManager;
    private Bus mBus;
    private Cart mCart;

    public CatalogPresenter(@NonNull CatalogContract.View view,
                            @NonNull JobManager jobManager,
                            @NonNull Bus bus,
                            @NonNull Cart cart){
        mView = view;
        mJobManager = jobManager;
        mBus = bus;
        mCart = cart;
    }

    @Override
    public void onStart() {
        mBus.register(this);
    }

    @Override
    public void onStop() {
        mBus.unregister(this);
    }

    @Override
    public void fetchCatalog(int page, int pageSize) {
        mJobManager.addJobInBackground(new FetchCatalogJob(page, pageSize));
    }

    @Override
    public void onAddToCartClick(@NonNull Product product) {
        onPlusClick(product);
    }

    @Override
    public void onProductClick(@NonNull Product product) {
        mView.showGoToProductDetail(product);
    }

    @Override
    public void onMinusClick(@NonNull Product product) {
        mCart.removeFromCart(product.getId(), 1);
        mView.showCartQuantityChange(product);
    }

    @Override
    public void onPlusClick(@NonNull Product product) {
        mCart.addToCart(product.getId(), 1);
        mView.showCartQuantityChange(product);
    }

    @Subscribe
    public void onReceiveEvent(FetchCatalogEvent event){
        if(event.isSucceed) {
            mView.addProductList(event.page, event.pageSize, event.productList);
        }
    }
}
