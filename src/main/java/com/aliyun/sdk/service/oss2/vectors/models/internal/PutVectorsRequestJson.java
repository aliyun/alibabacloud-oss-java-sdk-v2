package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * The request body schema for PutVectors operation.
 */
public class PutVectorsRequestJson {
    @JsonProperty("indexName")
    public String indexName;

    @JsonProperty("vectors")
    public List<Map<String, Object>> vectors;

}
