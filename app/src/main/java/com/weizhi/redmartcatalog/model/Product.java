package com.weizhi.redmartcatalog.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class Product {
    private final long mId;
    private final String mTitle;
    private final String mDescription;
    private final double mPrice;
    private final double mPromoPrice;
    private final boolean mIsOnSale;
    private final ProductImage mMainImage;
    private final ProductImage[] mImages;

    public Product(long mId,
                   @NonNull String mTitle,
                   @NonNull String mDescription,
                   double mPrice,
                   double mPromoPrice,
                   boolean mIsOnSale,
                   @NonNull ProductImage mMainImage,
                   @NonNull ProductImage[] mImages) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mPrice = mPrice;
        this.mPromoPrice = mPromoPrice;
        this.mIsOnSale = mIsOnSale;
        this.mMainImage = mMainImage;
        this.mImages = mImages;
    }

    public long getId(){
        return mId;
    }

    @NonNull
    public String getTitle(){
        return mTitle;
    }

    @NonNull
    public String getDescription(){
        return mDescription;
    }

    public double getPrice(){
        return mPrice;
    }

    public double getPromoPrice(){
        return mPromoPrice;
    }

    public boolean isOnSale(){
        return mIsOnSale;
    }

    @NonNull
    public ProductImage getMainImage(){
        return mMainImage;
    }
}
