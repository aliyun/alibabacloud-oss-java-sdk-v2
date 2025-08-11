package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.BucketInfoXml;

/**
 * The result for the GetBucketInfo operation.
 */
public final class GetBucketInfoResult extends ResultModel {
    private final BucketInfoXml delegate;

    GetBucketInfoResult(Builder builder) {
        super(builder);
        this.delegate = (BucketInfoXml) innerBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the information about the bucket.
     */
    public BucketInfo bucketInfo() {
        return delegate.bucket();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetBucketInfoResult result) {
            super(result);
        }

        public GetBucketInfoResult build() {
            return new GetBucketInfoResult(this);
        }
    }
}
