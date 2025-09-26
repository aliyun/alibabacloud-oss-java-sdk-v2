package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The request body schema for GetVectors operation.
 */
public class GetVectorsRequestJson {
    @JsonProperty("indexName")
    public String indexName;

    @JsonProperty("keys")
    public List<String> keys;

    @JsonProperty("returnData")
    public Boolean returnData;

    @JsonProperty("returnMetadata")
    public Boolean returnMetadata;

    public GetVectorsRequestJson() {
    }
}

