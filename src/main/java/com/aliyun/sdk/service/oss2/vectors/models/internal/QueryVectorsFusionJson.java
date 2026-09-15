package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsFusionSummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON model for fusion vector query result
 */
public class QueryVectorsFusionJson {
    @JsonProperty("vectors")
    public List<QueryVectorsFusionSummary> vectors;

    @JsonProperty("nextToken")
    public String nextToken;

    public QueryVectorsFusionJson() {
    }
}
