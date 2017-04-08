package com.weizhi.redmartcatalog.service.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.weizhi.redmartcatalog.MyApplication;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.network.RestResponse;
import com.weizhi.redmartcatalog.ui.uievent.FetchCatalogEvent;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class FetchCatalogJob extends Job {
    private static final int PRIORITY = 1;

    private int mPage ;
    private int mPageSize;

    public FetchCatalogJob(int page, int pageSize) {
        super(new Params(PRIORITY)
                .requireNetwork()
                .singleInstanceBy(page+";"+pageSize));
        mPage = page;
        mPageSize = pageSize;
    }

    @Override
    public void onAdded() {
        Timber.v("%s onAdded", getClass().getSimpleName());
    }

    @Override
    public void onRun() throws Throwable {
        final RestResponse<List<Product>> result = MyApplication.getRedmartWs().search(mPage, mPageSize);

        if(result.error == null &&
                200 <= result.httpStatusCode && result.httpStatusCode < 300){
            // Success
            Timber.i("Fetch catalog WS response success. Page: %d, PageSize: %d", mPage, mPageSize);
            MyApplication.getBus().post(new FetchCatalogEvent(true, result.payload, mPage, mPageSize));
        }
        else{
            // fail
            if(result.error != null){
                throw result.error;
            }
            else {
                throw new FailResponseCode(result.httpStatusCode);
            }
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        // Job has exceeded retry attempts or shouldReRunOnThrowable() has decided to cancel.
        MyApplication.getBus().post(
                new FetchCatalogEvent(false, new ArrayList<Product>(), mPage, mPageSize));
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable,
                                                     int runCount, int maxRunCount) {
        if(throwable instanceof FailResponseCode){
            int responseCode = ((FailResponseCode) throwable).responseCode;
            if(400 <= responseCode && responseCode < 500){
                return RetryConstraint.CANCEL;
            }
        }

        RetryConstraint constraint = RetryConstraint.createExponentialBackoff(runCount, 500);
        return constraint;
    }
}