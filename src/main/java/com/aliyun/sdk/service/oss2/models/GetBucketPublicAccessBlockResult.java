package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketPublicAccessBlock operation.
 */
public final class GetBucketPublicAccessBlockResult extends ResultModel { 

    /**
     * The container in which the Block Public Access configurations are stored.
     */
    public BucketPublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return (BucketPublicAccessBlockConfiguration)innerBody;
    } 
     

    GetBucketPublicAccessBlockResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketPublicAccessBlockResult build() {
            return new GetBucketPublicAccessBlockResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketPublicAccessBlockResult result) {
            super(result);
        }
    }
}
