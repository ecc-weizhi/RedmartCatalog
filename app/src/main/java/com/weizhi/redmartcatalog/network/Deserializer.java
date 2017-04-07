package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface Deserializer<F, T> {
    @NonNull
    T deserializeFrom(@NonNull F from);
}
