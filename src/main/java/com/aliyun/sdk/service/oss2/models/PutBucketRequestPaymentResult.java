package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketRequestPayment operation.
 */
public final class PutBucketRequestPaymentResult extends ResultModel { 

    PutBucketRequestPaymentResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketRequestPaymentResult build() {
            return new PutBucketRequestPaymentResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketRequestPaymentResult result) {
            super(result);
        }
    }
}
