package com.weizhi.redmartcatalog.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    private final String mWtVol;
    private final int mExpiryTime;
    private final String mExpiryMetric;
    private final boolean mIsExpiryMinimum;
    private final ProductImage mMainImage;
    private final ProductImage[] mImages;
    private final boolean mIsFrozen;

    public Product(long id,
                   @NonNull String title,
                   @NonNull String description,
                   double price,
                   double promoPrice,
                   boolean isOnSale,
                   @Nullable String wtVol,
                   int expiryTime,
                   @Nullable String expiryMetric,
                   boolean isExpiryMinimum,
                   @NonNull ProductImage mainImage,
                   @NonNull ProductImage[] images,
                   boolean isFrozen) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mPrice = price;
        mPromoPrice = promoPrice;
        mIsOnSale = isOnSale;
        mWtVol = wtVol;
        mExpiryTime = expiryTime;
        mExpiryMetric = expiryMetric;
        mIsExpiryMinimum = isExpiryMinimum;
        mMainImage = mainImage;
        mImages = images;
        mIsFrozen = isFrozen;
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

    @Nullable
    public String getWtVol(){
        return mWtVol;
    }

    @Nullable
    public String getExpiry(){
        if(mExpiryTime == 0){
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(mExpiryTime).append(mExpiryMetric);
        if(mIsExpiryMinimum){
            sb.append("+");
        }
        return sb.toString();
    }

    @NonNull
    public ProductImage getMainImage(){
        return mMainImage;
    }

    public boolean isFrozen(){
        return mIsFrozen;
    }
}
