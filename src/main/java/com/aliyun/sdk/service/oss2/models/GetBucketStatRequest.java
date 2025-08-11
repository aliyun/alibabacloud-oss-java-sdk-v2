package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketStat operation.
 */
public final class GetBucketStatRequest extends RequestModel {
    private final String bucket;

    private GetBucketStatRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The bucket about which you want to query the information.
     */
    public String bucket() {
        return bucket;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }


        private Builder(GetBucketStatRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * The bucket about which you want to query the information.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public GetBucketStatRequest build() {
            return new GetBucketStatRequest(this);
        }
    }

}
