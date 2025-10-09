package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsSummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON model for vector query result
 */
public class QueryVectorsJson {
    @JsonProperty("vectors")
    public List<QueryVectorsSummary> vectors;
    
    public QueryVectorsJson() {
    }
}