package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketDataRedundancyTransition operation.
 */
public final class GetBucketDataRedundancyTransitionResult extends ResultModel { 

    /**
     * The container for a specific redundancy type change task.
     */
    public BucketDataRedundancyTransition bucketDataRedundancyTransition() {
        return (BucketDataRedundancyTransition)innerBody;
    } 
     

    GetBucketDataRedundancyTransitionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketDataRedundancyTransitionResult build() {
            return new GetBucketDataRedundancyTransitionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketDataRedundancyTransitionResult result) {
            super(result);
        }
    }
}
