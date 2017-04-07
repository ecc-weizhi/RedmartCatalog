package com.weizhi.redmartcatalog.network.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductLife {
    @JsonProperty("time") public int time;
    @JsonProperty("metric") public String metric;
    @JsonProperty("is_minimum") public boolean isMinimum;
}
