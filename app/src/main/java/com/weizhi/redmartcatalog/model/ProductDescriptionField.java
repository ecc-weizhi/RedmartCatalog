package com.weizhi.redmartcatalog.model;

import android.support.annotation.Nullable;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDescriptionField {
    private final String mName;
    private final String mContent;

    public ProductDescriptionField(String mName, String mContent) {
        this.mName = mName;
        this.mContent = mContent;
    }

    @Nullable
    public String getName(){
        return mName;
    }

    @Nullable
    public String getContent(){
        return mContent;
    }
}
