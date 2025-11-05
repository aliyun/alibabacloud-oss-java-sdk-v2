package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketLogging operation.
 */
public final class GetBucketLoggingResult extends ResultModel { 

    /**
     * Indicates the container used to store access logging configuration of a bucket.
     */
    public BucketLoggingStatus bucketLoggingStatus() {
        return (BucketLoggingStatus)innerBody;
    } 
     

    GetBucketLoggingResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketLoggingResult build() {
            return new GetBucketLoggingResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketLoggingResult result) {
            super(result);
        }
    }
}
