package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketTransferAcceleration operation.
 */
public final class GetBucketTransferAccelerationResult extends ResultModel { 

    /**
     * The container that stores the transfer acceleration configurations.
     */
    public TransferAccelerationConfiguration transferAccelerationConfiguration() {
        return (TransferAccelerationConfiguration)innerBody;
    } 
     

    GetBucketTransferAccelerationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketTransferAccelerationResult build() {
            return new GetBucketTransferAccelerationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketTransferAccelerationResult result) {
            super(result);
        }
    }
}
