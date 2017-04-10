package com.weizhi.redmartcatalog.ui.catalog;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
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

    public CatalogPresenter(@NonNull CatalogContract.View view,
                            @NonNull JobManager jobManager,
                            @NonNull Bus bus){
        mView = view;
        mJobManager = jobManager;
        mBus = bus;
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
        mView.showAddedToCart(product);
    }

    @Override
    public void onProductClick(@NonNull Product product) {
        mView.showGoToProductDetail(product);
    }

    @Subscribe
    public void onReceiveEvent(FetchCatalogEvent event){
        if(event.isSucceed) {
            mView.addProductList(event.page, event.pageSize, event.productList);
        }
    }
}
