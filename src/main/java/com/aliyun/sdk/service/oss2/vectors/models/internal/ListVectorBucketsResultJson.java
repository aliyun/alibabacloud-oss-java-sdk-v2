package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.BucketsSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListVectorBucketsResultJson {
    @JsonProperty("ListAllMyBucketsResult")
    public BucketsSummary bucketsSummary;

    public ListVectorBucketsResultJson() {
    }
}