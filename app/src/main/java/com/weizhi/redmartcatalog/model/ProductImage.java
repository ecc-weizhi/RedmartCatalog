package com.weizhi.redmartcatalog.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductImage implements Serializable{
    private final int mHeight;
    private final int mWidth;
    private final String mPath;
    private final int mPosition;

    public ProductImage(int height, int width, @NonNull String path, int position) {
        mHeight = height;
        mWidth = width;
        mPath = path;
        mPosition = position;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public String getPath() {
        return mPath;
    }

    public int getPosition() {
        return mPosition;
    }
}
