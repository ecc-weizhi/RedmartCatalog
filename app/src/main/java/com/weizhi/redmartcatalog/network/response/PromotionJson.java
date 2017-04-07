package com.weizhi.redmartcatalog.network.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionJson {
    @JsonProperty("id") public int id;
    @JsonProperty("type") public int type;
    @JsonProperty("savings_text") public String savingsText;
    @JsonProperty("promo_label") public String promoLabel;
    @JsonProperty("current_product_group_id") public int currentProductGroupId;
}
