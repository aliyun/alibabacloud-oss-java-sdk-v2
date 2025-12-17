package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateBucketDataRedundancyTransition operation.
 */
public final class CreateBucketDataRedundancyTransitionResult extends ResultModel { 

    /**
     * The container in which the redundancy type conversion task is stored.
     */
    public BucketDataRedundancyTransition bucketDataRedundancyTransition() {
        return (BucketDataRedundancyTransition)innerBody;
    } 
     

    CreateBucketDataRedundancyTransitionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CreateBucketDataRedundancyTransitionResult build() {
            return new CreateBucketDataRedundancyTransitionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateBucketDataRedundancyTransitionResult result) {
            super(result);
        }
    }
}
