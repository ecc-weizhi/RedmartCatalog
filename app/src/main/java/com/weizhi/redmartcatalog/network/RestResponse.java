package com.weizhi.redmartcatalog.network;

import android.support.annotation.Nullable;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class RestResponse<T> {
    public final T payload;
    public final Integer httpStatusCode;
    public final Throwable error;

    RestResponse(@Nullable T payload,
                 @Nullable Integer httpStatusCode,
                 @Nullable Throwable error){
        this.payload = payload;
        this.httpStatusCode = httpStatusCode;
        this.error = error;
    }
}