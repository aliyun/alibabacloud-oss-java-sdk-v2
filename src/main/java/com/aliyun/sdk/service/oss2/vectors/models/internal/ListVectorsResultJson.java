package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON model for list vectors result
 */
public class ListVectorsResultJson {
    @JsonProperty("nextToken")
    public String nextToken;

    @JsonProperty("vectors")
    public List<VectorsSummary> vectors;
    
    public ListVectorsResultJson() {
    }
}