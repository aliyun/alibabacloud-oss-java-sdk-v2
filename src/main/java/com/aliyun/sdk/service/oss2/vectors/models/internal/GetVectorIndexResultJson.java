package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.IndexSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetVectorIndexResultJson {
    @JsonProperty("index")
    public IndexSummary index;
}
