package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

interface Deserializer<F, T> {
    @NonNull
    T deserializeFrom(@NonNull F from);
}
