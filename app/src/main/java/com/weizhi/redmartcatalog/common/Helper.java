package com.weizhi.redmartcatalog.common;

import android.content.Context;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class Helper {
    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
