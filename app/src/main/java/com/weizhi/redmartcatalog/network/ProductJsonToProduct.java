package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductDescriptionField;
import com.weizhi.redmartcatalog.model.ProductImage;
import com.weizhi.redmartcatalog.network.jsonpojo.DescriptionSecondaryJson;
import com.weizhi.redmartcatalog.network.jsonpojo.ImageJson;
import com.weizhi.redmartcatalog.network.jsonpojo.ProductJson;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

class ProductJsonToProduct implements Deserializer<ProductJson, Product> {
    @NonNull
    @Override
    public Product deserializeFrom(@NonNull ProductJson from) {
        ProductImage mainImage = new ProductImage(from.img.h,
                from.img.w,
                from.img.name,
                from.img.position);

        ProductImage[] images = new ProductImage[from.images == null ? 0 : from.images.size()];
        for(int i=0; i<images.length; i++){
            ImageJson json = from.images.get(i);
            images[i] = new ProductImage(json.h, json.w, json.name, json.position);
        }

        int size = 0;
        if(from.descriptionField != null && from.descriptionField.secondaryList != null){
            size = from.descriptionField.secondaryList.size();
        }
        ProductDescriptionField[] secondaryDescriptions = new ProductDescriptionField[size];
        for(int i=0; i<secondaryDescriptions.length; i++){
            DescriptionSecondaryJson secondary = from.descriptionField.secondaryList.get(i);
            ProductDescriptionField description = new ProductDescriptionField(
                    secondary.name, secondary.content);
            secondaryDescriptions[i] = description;
        }

        int promotionType = 0;
        String savingText = null;
        if(from.promotions != null && from.promotions.size() > 0){
            promotionType = from.promotions.get(0).type;
            savingText = from.promotions.get(0).savingsText;
        }

        double price = from.pricing == null ? 0 : from.pricing.price;
        double promoPrice = from.pricing == null ? 0 : from.pricing.promoPrice;
        boolean isOnSale = from.pricing != null && from.pricing.onSale != 0;
        String wtVol = from.measure == null ? null : from.measure.wtOrVol;
        int expiryTime = from.productLife == null ? 0 : from.productLife.time;
        String expiryMetric = from.productLife == null ? null : from.productLife.metric;
        boolean isExpiryMinimum = from.productLife != null && from.productLife.isMinimum;
        boolean isFrozen = from.labels != null && from.labels.contains("frozen");

        return new Product(from.id,
                from.title,
                from.desc,
                price,
                promoPrice,
                isOnSale,
                wtVol,
                expiryTime,
                expiryMetric,
                isExpiryMinimum,
                mainImage,
                images,
                isFrozen,
                secondaryDescriptions,
                promotionType,
                savingText,
                null);
    }
}
