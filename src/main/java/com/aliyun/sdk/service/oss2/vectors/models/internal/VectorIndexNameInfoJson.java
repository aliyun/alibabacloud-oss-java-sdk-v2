package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model for vector index name info
 */
public class VectorIndexNameInfoJson {
    @JsonProperty("indexName")
    public String indexName;

    public VectorIndexNameInfoJson() {
    }
}
