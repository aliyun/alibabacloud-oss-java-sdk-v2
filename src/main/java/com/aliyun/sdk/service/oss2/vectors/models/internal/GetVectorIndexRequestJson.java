package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model for get vector index request
 */
public class GetVectorIndexRequestJson {
    @JsonProperty("indexName")
    public String indexName;

    public GetVectorIndexRequestJson() {
    }
}
