package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductImage;
import com.weizhi.redmartcatalog.network.jsonpojo.ImageJson;
import com.weizhi.redmartcatalog.network.jsonpojo.ProductJson;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductJsonToProduct implements Deserializer<ProductJson, Product> {
    @NonNull
    @Override
    public Product deserializeFrom(@NonNull ProductJson from) {
        ProductImage mainImage = new ProductImage(from.img.h,
                from.img.w,
                from.img.name,
                from.img.position);

        ProductImage[] images = new ProductImage[from.images.size()];
        for(int i=0; i<from.images.size(); i++){
            ImageJson json = from.images.get(i);
            images[i] = new ProductImage(json.h, json.w, json.name, json.position);
        }

        double price = from.pricing == null ? 0 : from.pricing.price;
        double promoPrice = from.pricing == null ? 0 : from.pricing.promoPrice;
        boolean isOnSale = from.pricing == null ? false : from.pricing.onSale != 0;
        String wtVol = from.measure == null ? null : from.measure.wtOrVol;
        int expiryTime = from.productLife == null ? 0 : from.productLife.time;
        String expiryMetric = from.productLife == null ? null : from.productLife.metric;
        boolean isExpiryMinimum = from.productLife == null ? false : from.productLife.isMinimum;
        boolean isFrozen = from.labels == null ? false : from.labels.contains("frozen");

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
                isFrozen);
    }
}
