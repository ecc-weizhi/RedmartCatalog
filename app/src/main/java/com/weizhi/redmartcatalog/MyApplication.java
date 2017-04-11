package com.weizhi.redmartcatalog;

import android.app.Application;
import android.os.Build;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.otto.Bus;
import com.weizhi.redmartcatalog.model.Cart;
import com.weizhi.redmartcatalog.network.RedmartWs;
import com.weizhi.redmartcatalog.network.RedmartWsImpl;
import com.weizhi.redmartcatalog.network.WsConstants;
import com.weizhi.redmartcatalog.service.NetworkGcmJobService;
import com.weizhi.redmartcatalog.service.NetworkJobService;

import timber.log.Timber;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class MyApplication extends Application {
    private static MyApplication mContext;
    private static Bus bus;
    private static RedmartWs mRedmartWs;
    private static NetworkChangeReceiver mNetworkChangeReceiver;
    private Cart mCart;
    private JobManager jobManager;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = this;
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        getBus().register(this);
        getJobManager();// ensure it is created
        mRedmartWs = new RedmartWsImpl(WsConstants.BASE_URL, WsConstants.API_VERSION);
    }

    public static NetworkChangeReceiver getNetworkChangeReceiver(){
        if(mNetworkChangeReceiver == null){
            mNetworkChangeReceiver = new NetworkChangeReceiver();
        }

        return mNetworkChangeReceiver;
    }

    public static MyApplication getInstance(){
        return mContext;
    }

    public static Bus getBus(){
        if(bus == null){
            bus = new MainThreadBus();
        }

        return bus;
    }

    public static RedmartWs getRedmartWs(){
        return mRedmartWs;
    }

    private void configureJobManager() {
        Configuration.Builder builder = new Configuration.Builder(this)
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(1)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120);//wait 2 minute
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(this,
                    NetworkJobService.class), true);
        } else {
            int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            if (enableGcm == ConnectionResult.SUCCESS) {
                builder.scheduler(GcmJobSchedulerService.createSchedulerFor(this,
                        NetworkGcmJobService.class), true);
            }
        }
        jobManager = new JobManager(builder.build());
    }

    public synchronized JobManager getJobManager() {
        if (jobManager == null) {
            configureJobManager();
        }
        return jobManager;
    }

    public Cart getCart(){
        if(mCart == null){
            mCart = new Cart(Cart.MAX_QUANTITY);
        }
        return mCart;
    }
}