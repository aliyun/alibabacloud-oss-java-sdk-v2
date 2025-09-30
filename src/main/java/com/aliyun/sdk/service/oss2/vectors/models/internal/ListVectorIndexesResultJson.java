package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * JSON model for list vector indexes result
 */
public class ListVectorIndexesResultJson {

    @JsonProperty("indexes")
    public List<Map<String, Object>> indexes;

    @JsonProperty("nextToken")
    public String nextToken;

    public ListVectorIndexesResultJson() {
    }
}
