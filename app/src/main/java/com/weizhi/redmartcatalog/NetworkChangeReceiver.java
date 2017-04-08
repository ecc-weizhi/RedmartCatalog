package com.weizhi.redmartcatalog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.weizhi.redmartcatalog.ui.uievent.NetworkChangeEvent;

import timber.log.Timber;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Timber.d("Network connectivity change");

        if (intent.getExtras() != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

            if (ni != null && ni.isConnectedOrConnecting()) {
                // Connected
                Timber.d("Network %s connected", ni.getTypeName());
                context.unregisterReceiver(this);
                MyApplication.getBus().post(new NetworkChangeEvent(true));
            }
            else{
                // Not connected
                Timber.d("Network not connected");
                MyApplication.getBus().post(new NetworkChangeEvent(false));
            }
        }
    }
}
