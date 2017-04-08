package com.weizhi.redmartcatalog.service;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.weizhi.redmartcatalog.MyApplication;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class NetworkJobService extends FrameworkJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return MyApplication.getInstance().getJobManager();
    }
}