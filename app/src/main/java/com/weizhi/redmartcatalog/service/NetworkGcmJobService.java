package com.weizhi.redmartcatalog.service;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.weizhi.redmartcatalog.MyApplication;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class NetworkGcmJobService extends GcmJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return MyApplication.getInstance().getJobManager();
    }
}