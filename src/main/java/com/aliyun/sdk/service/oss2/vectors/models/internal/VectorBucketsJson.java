package com.aliyun.sdk.service.oss2.vectors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The container that stores the vector buckets information.
 */
public final class VectorBucketsJson {
    @JsonProperty("Prefix")
    private String prefix;

    @JsonProperty("Marker")
    private String marker;

    @JsonProperty("MaxKeys")
    private Integer maxKeys;

    @JsonProperty("IsTruncated")
    private Boolean isTruncated;

    @JsonProperty("NextMarker")
    private String nextMarker;

    @JsonProperty("Buckets")
    private List<BucketProperties> buckets;

    public VectorBucketsJson() {
    }

    private VectorBucketsJson(Builder builder) {
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

    /**
     * The prefix that the names of returned buckets must contain.
     */
    public String prefix() {
        return this.prefix;
    }

    /**
     * The name of the bucket from which the list operation begins.
     */
    public String marker() {
        return this.marker;
    }

    /**
     * The maximum number of buckets that can be returned in the single query.
     */
    public Integer maxKeys() {
        return this.maxKeys;
    }

    /**
     * Indicates whether the list of buckets is truncated.
     */
    public Boolean isTruncated() {
        return this.isTruncated;
    }

    /**
     * The marker for the next list operation.
     */
    public String nextMarker() {
        return this.nextMarker;
    }

    /**
     * The list of buckets.
     */
    public List<BucketProperties> buckets() {
        return this.buckets;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String prefix;
        private String marker;
        private Integer maxKeys;
        private Boolean isTruncated;
        private String nextMarker;
        private List<BucketProperties> buckets;

        private Builder() {
            super();
        }

        private Builder(VectorBucketsJson from) {
            this.prefix = from.prefix;
            this.marker = from.marker;
            this.maxKeys = from.maxKeys;
            this.isTruncated = from.isTruncated;
            this.nextMarker = from.nextMarker;
            this.buckets = from.buckets;
        }

        /**
         * The prefix that the names of returned buckets must contain.
         */
        public Builder prefix(String value) {
            this.prefix = value;
            return this;
        }

        /**
         * The name of the bucket from which the list operation begins.
         */
        public Builder marker(String value) {
            this.marker = value;
            return this;
        }

        /**
         * The maximum number of buckets that can be returned in the single query.
         */
        public Builder maxKeys(Integer value) {
            this.maxKeys = value;
            return this;
        }

        /**
         * Indicates whether the list of buckets is truncated.
         */
        public Builder isTruncated(Boolean value) {
            this.isTruncated = value;
            return this;
        }

        /**
         * The marker for the next list operation.
         */
        public Builder nextMarker(String value) {
            this.nextMarker = value;
            return this;
        }

        /**
         * The list of buckets.
         */
        public Builder buckets(List<BucketProperties> value) {
            this.buckets = value;
            return this;
        }

        public VectorBucketsJson build() {
            return new VectorBucketsJson(this);
        }
    }
}
