package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for VectorBucket to match the JSON structure.
 */
public class VectorBucketInfo {
    @JsonProperty("BucketInfo")
    private VectorBucket bucketInfo;

    public VectorBucketInfo() {
    }

    public VectorBucket getBucketInfo() {
        return bucketInfo;
    }

    public void setBucketInfo(VectorBucket bucketInfo) {
        this.bucketInfo = bucketInfo;
    }
}