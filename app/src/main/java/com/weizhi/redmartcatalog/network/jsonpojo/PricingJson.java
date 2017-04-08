package com.weizhi.redmartcatalog.network.jsonpojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingJson {
    @JsonProperty("on_sale") public int onSale;
    @JsonProperty("price") public double price;
    @JsonProperty("promo_price") public double promoPrice;
    @JsonProperty("savings") public int savings;
    @JsonProperty("savings_amount") public double savingAmount;
    @JsonProperty("savings_type") public int savingsType;
    @JsonProperty("savings_text") public String savingsText;
    @JsonProperty("promo_id") public int promoId;
}
