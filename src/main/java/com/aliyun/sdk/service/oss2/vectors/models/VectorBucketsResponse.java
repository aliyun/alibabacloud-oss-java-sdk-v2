package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorBucketsJson;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VectorBucketsResponse {
    @JsonProperty("ListAllMyBucketsResult")
    private VectorBucketsJson vectorBuckets;

    public VectorBucketsJson getVectorBuckets() {
        return vectorBuckets;
    }

    public void setVectorBuckets(VectorBucketsJson vectorBuckets) {
        this.vectorBuckets = vectorBuckets;
    }
}
