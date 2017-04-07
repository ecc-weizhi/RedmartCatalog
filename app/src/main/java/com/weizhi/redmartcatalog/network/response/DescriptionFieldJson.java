package com.weizhi.redmartcatalog.network.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DescriptionFieldJson {
    @JsonProperty("primary") public List<DescriptionPrimaryJson> primaryList;
    @JsonProperty("secondary") public List<DescriptionSecondaryJson> secondaryList;
}
