package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketLifecycle operation.
 */
public final class GetBucketLifecycleResult extends ResultModel { 

    /**
     * The container that stores the lifecycle rules configured for the bucket.
     */
    public LifecycleConfiguration lifecycleConfiguration() {
        return (LifecycleConfiguration)innerBody;
    } 
     

    GetBucketLifecycleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketLifecycleResult build() {
            return new GetBucketLifecycleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketLifecycleResult result) {
            super(result);
        }
    }
}
