package com.weizhi.redmartcatalog.network.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasureJson {
    @JsonProperty("wt_or_vol") public String wtOrVol;
    @JsonProperty("size") public int size;
}
