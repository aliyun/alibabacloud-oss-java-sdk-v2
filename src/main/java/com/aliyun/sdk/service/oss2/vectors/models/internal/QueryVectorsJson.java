package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * JSON model for vector list result
 */
public class QueryVectorsJson {
    @JsonProperty("vectors")
    public List<Map<String, Object>> vectors;
    
    public QueryVectorsJson() {
    }
}