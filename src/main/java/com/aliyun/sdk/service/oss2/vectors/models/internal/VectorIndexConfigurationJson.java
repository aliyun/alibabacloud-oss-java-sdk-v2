package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * The configuration for the vector index.
 */
public final class VectorIndexConfigurationJson {
    @JsonProperty("dataType")
    public String dataType;

    @JsonProperty("dimension")
    public Integer dimension;

    @JsonProperty("distanceMetric")
    public String distanceMetric;

    @JsonProperty("indexName")
    public String indexName;

    @JsonProperty("metadata")
    public Map<String, Object> metadata;

    public VectorIndexConfigurationJson() {
    }

}
