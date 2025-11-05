package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.VectorBucketInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetVectorBucketResultJson {
    @JsonProperty("BucketInfo")
    public VectorBucketInfo bucketInfo;
}