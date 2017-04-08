package com.weizhi.redmartcatalog.ui.uievent;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class NetworkChangeEvent {
    public final boolean isNetworkAvailable;

    public NetworkChangeEvent(boolean isNetworkAvailable) {
        this.isNetworkAvailable = isNetworkAvailable;
    }
}
