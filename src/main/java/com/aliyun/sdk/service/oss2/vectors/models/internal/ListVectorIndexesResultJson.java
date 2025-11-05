package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.IndexSummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON model for list vector indexes result
 */
public class ListVectorIndexesResultJson {

    @JsonProperty("indexes")
    public List<IndexSummary> indexes;

    @JsonProperty("nextToken")
    public String nextToken;

    public ListVectorIndexesResultJson() {
    }
}