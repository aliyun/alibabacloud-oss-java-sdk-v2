package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The summary of vector buckets.
 */
public class BucketsSummary {
    @JsonProperty("Prefix")
    private String prefix;
    @JsonProperty("Marker")
    private String marker;
    @JsonProperty("MaxKeys")
    private Long maxKeys;
    @JsonProperty("IsTruncated")
    private Boolean isTruncated;
    @JsonProperty("NextMarker")
    private String nextMarker;
    @JsonProperty("Buckets")
    private List<VectorBucketSummary> buckets;

    public BucketsSummary() {
    }

    private BucketsSummary(Builder builder) {
        this.prefix = builder.prefix;
        this.marker = builder.marker;
        this.maxKeys = builder.maxKeys;
        this.isTruncated = builder.isTruncated;
        this.nextMarker = builder.nextMarker;
        this.buckets = builder.buckets;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String prefix() {
        return prefix;
    }

    public String marker() {
        return marker;
    }

    public Long maxKeys() {
        return maxKeys;
    }

    public Boolean isTruncated() {
        return isTruncated;
    }

    public String nextMarker() {
        return nextMarker;
    }

    public List<VectorBucketSummary> buckets() {
        return buckets;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String prefix;
        private String marker;
        private Long maxKeys;
        private Boolean isTruncated;
        private String nextMarker;
        private List<VectorBucketSummary> buckets;

        private Builder() {
        }

        private Builder(BucketsSummary from) {
            this.prefix = from.prefix;
            this.marker = from.marker;
            this.maxKeys = from.maxKeys;
            this.isTruncated = from.isTruncated;
            this.nextMarker = from.nextMarker;
            this.buckets = from.buckets;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder marker(String marker) {
            this.marker = marker;
            return this;
        }

        public Builder maxKeys(Long maxKeys) {
            this.maxKeys = maxKeys;
            return this;
        }

        public Builder isTruncated(Boolean isTruncated) {
            this.isTruncated = isTruncated;
            return this;
        }

        public Builder nextMarker(String nextMarker) {
            this.nextMarker = nextMarker;
            return this;
        }

        public Builder buckets(List<VectorBucketSummary> buckets) {
            this.buckets = buckets;
            return this;
        }

        public BucketsSummary build() {
            return new BucketsSummary(this);
        }
    }
}