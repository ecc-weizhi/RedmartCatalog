package com.weizhi.redmartcatalog.network.jsonpojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductJson {
    @JsonProperty("id") public long id;
    @JsonProperty("title") public String title;
    @JsonProperty("desc") public String desc;
    @JsonProperty("product_life") public ProductLife productLife;
    @JsonProperty("img") public ImageJson img;
    @JsonProperty("images") public List<ImageJson> images;
    @JsonProperty("measure") public MeasureJson measure;
    @JsonProperty("pricing") public PricingJson pricing;
    @JsonProperty("description_fields") public DescriptionFieldJson descriptionField;
    @JsonProperty("promotions") public List<PromotionJson> promotions;
}
