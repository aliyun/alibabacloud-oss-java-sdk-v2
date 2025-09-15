package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.vectors.models.internal.BucketInfoJson;
import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Objects.requireNonNull;

public class BucketInfoResponse {
    @JsonProperty("BucketInfo")
    private BucketInfoJson bucketInfo;

    public BucketInfoResponse() {
    }

    private BucketInfoResponse(Builder builder) {
        this.bucketInfo = builder.bucketInfo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the bucket information.
     */
    public BucketInfoJson bucketInfo() {
        return bucketInfo;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private BucketInfoJson bucketInfo;

        private Builder() {
            super();
        }

        private Builder(BucketInfoResponse from) {
            this.bucketInfo = from.bucketInfo;
        }

        /**
         * The container that stores the bucket information.
         */
        public Builder bucketInfo(BucketInfoJson value) {
            requireNonNull(value);
            this.bucketInfo = value;
            return this;
        }

        public BucketInfoResponse build() {
            return new BucketInfoResponse(this);
        }
    }
}
