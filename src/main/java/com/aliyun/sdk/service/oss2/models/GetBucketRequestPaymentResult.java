package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketRequestPayment operation.
 */
public final class GetBucketRequestPaymentResult extends ResultModel { 

    /**
     * Indicates the container for the payer.
     */
    public RequestPaymentConfiguration requestPaymentConfiguration() {
        return (RequestPaymentConfiguration)innerBody;
    } 
     

    GetBucketRequestPaymentResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketRequestPaymentResult build() {
            return new GetBucketRequestPaymentResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketRequestPaymentResult result) {
            super(result);
        }
    }
}
