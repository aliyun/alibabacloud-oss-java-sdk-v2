package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model for listing vector indexes
 */
public class ListVectorIndexesRequestJson {
    @JsonProperty("maxResults")
    public Long maxResults;

    @JsonProperty("nextToken")
    public String nextToken;

    @JsonProperty("prefix")
    public String prefix;

    public ListVectorIndexesRequestJson() {
    }
}
