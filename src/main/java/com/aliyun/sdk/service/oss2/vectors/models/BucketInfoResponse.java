package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.vectors.models.internal.BucketInfoJson;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BucketInfoResponse {
    @JsonProperty("BucketInfo")
    private BucketInfoJson bucketInfo;

    public BucketInfoJson getBucketInfo() {
        return bucketInfo;
    }

    public void setBucketInfo(BucketInfoJson bucketInfo) {
        this.bucketInfo = bucketInfo;
    }
}
