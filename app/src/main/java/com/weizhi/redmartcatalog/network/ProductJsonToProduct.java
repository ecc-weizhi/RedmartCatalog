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

        return new Product(from.id,
                from.title,
                from.desc,
                from.pricing.price,
                from.pricing.promoPrice,
                from.pricing.onSale != 0,
                mainImage,
                images);
    }
}
