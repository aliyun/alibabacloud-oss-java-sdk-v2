package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GetVectorsResultJson {
    @JsonProperty("vectors")
    public List<VectorsSummary> vectors;
    
    public GetVectorsResultJson() {
    }
}