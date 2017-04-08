package com.weizhi.redmartcatalog.network.jsonpojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageJson {
    @JsonProperty("h") public int h;
    @JsonProperty("w") public int w;
    @JsonProperty("name") public String name;
    @JsonProperty("position") public int position;
}
