package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketStat operation.
 */
public final class GetBucketStatResult extends ResultModel {

    GetBucketStatResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores all information returned for the GetBucketStat request.
     */
    public BucketStat bucketStat() {
        return (BucketStat) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetBucketStatResult result) {
            super(result);
        }

        public GetBucketStatResult build() {
            return new GetBucketStatResult(this);
        }
    }
}
