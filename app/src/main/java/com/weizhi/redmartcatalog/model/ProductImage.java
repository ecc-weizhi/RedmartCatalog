package com.weizhi.redmartcatalog.model;

import android.support.annotation.NonNull;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductImage {
    public final int height;
    public final int width;
    public final String path;
    public final int position;

    public ProductImage(int height, int width, @NonNull String path, int position) {
        this.height = height;
        this.width = width;
        this.path = path;
        this.position = position;
    }
}
