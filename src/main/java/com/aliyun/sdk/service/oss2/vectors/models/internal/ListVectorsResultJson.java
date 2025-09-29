package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * JSON model for list vectors result
 */
public class ListVectorsResultJson {
    @JsonProperty("nextToken")
    public String nextToken;

    @JsonProperty("vectors")
    public List<Map<String, Object>> vectors;

    public ListVectorsResultJson() {
    }
}