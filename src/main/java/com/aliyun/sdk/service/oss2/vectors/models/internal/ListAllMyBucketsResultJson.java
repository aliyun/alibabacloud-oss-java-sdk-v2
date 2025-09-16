package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.aliyun.sdk.service.oss2.vectors.models.VectorBucketProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class ListAllMyBucketsResultJson {
    @JsonProperty("ListAllMyBucketsResult")
    public ListAllMyBucketsResult listAllMyBucketsResult;

    public ListAllMyBucketsResultJson() {
    }

    public static class ListAllMyBucketsResult {
        @JsonProperty("Prefix")
        public String prefix;

        @JsonProperty("Marker")
        public String marker;

        @JsonProperty("MaxKeys")
        public Integer maxKeys;

        @JsonProperty("IsTruncated")
        public Boolean isTruncated;

        @JsonProperty("NextMarker")
        public String nextMarker;

        @JsonProperty("Buckets")
        public List<VectorBucketProperties> buckets;

        public ListAllMyBucketsResult() {
        }


    }
}
