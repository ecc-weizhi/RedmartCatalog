package com.weizhi.redmartcatalog.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class Product implements Serializable{
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
    private final ProductDescriptionField[] mSecondaryDescriptionField;
    private final int mPromotionType;
    private final String mSavingText;

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
                   boolean isFrozen,
                   @NonNull ProductDescriptionField[] secondaryDescriptionField,
                   int promotionType,
                   @Nullable String savingText) {
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
        mSecondaryDescriptionField = secondaryDescriptionField;
        mPromotionType = promotionType;
        mSavingText = savingText;
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

    public int getExpiryTime(){
        return mExpiryTime;
    }

    @Nullable
    public String getExpiryMetric(){
        return mExpiryMetric;
    }

    public boolean isExpiryMinimum(){
        return mIsExpiryMinimum;
    }

    @NonNull
    public ProductImage getMainImage(){
        return mMainImage;
    }

    @NonNull
    public ProductImage[] getImages(){
        return mImages;
    }

    public boolean isFrozen(){
        return mIsFrozen;
    }

    @NonNull
    public ProductDescriptionField[] getSecondaryDescriptionField(){
        return mSecondaryDescriptionField;
    }

    public int getPromotionType(){
        return mPromotionType;
    }

    @Nullable
    public String getSavingText(){
        return mSavingText;
    }
}
