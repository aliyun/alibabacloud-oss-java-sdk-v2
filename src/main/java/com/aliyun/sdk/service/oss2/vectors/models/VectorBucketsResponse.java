package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorBucketsJson;
import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Objects.requireNonNull;

public class VectorBucketsResponse {
    @JsonProperty("ListAllMyBucketsResult")
    private VectorBucketsJson vectorBuckets;

    public VectorBucketsResponse() {
    }

    private VectorBucketsResponse(Builder builder) {
        this.vectorBuckets = builder.vectorBuckets;
    }

    public VectorBucketsJson vectorBuckets() {
        return vectorBuckets;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private VectorBucketsJson vectorBuckets;

        private Builder() {
            super();
        }

        private Builder(VectorBucketsResponse from) {
            this.vectorBuckets = from.vectorBuckets;
        }

        /**
         * The container that stores the vector buckets information.
         */
        public Builder vectorBuckets(VectorBucketsJson value) {
            requireNonNull(value);
            this.vectorBuckets = value;
            return this;
        }

        public VectorBucketsResponse build() {
            return new VectorBucketsResponse(this);
        }
    }
}

