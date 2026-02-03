package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketRequestPayment operation.
 */
public final class PutBucketRequestPaymentRequest extends RequestModel { 
    private final String bucket;
    private final RequestPaymentConfiguration requestPaymentConfiguration;

    private PutBucketRequestPaymentRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.requestPaymentConfiguration = builder.requestPaymentConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The request body schema.
     */
    public RequestPaymentConfiguration requestPaymentConfiguration() {
        return requestPaymentConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private RequestPaymentConfiguration requestPaymentConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The request body schema.
        */
        public Builder requestPaymentConfiguration(RequestPaymentConfiguration value) {
            requireNonNull(value);
            this.requestPaymentConfiguration = value;
            return this;        
        }
        
        public PutBucketRequestPaymentRequest build() {
            return new PutBucketRequestPaymentRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketRequestPaymentRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.requestPaymentConfiguration = request.requestPaymentConfiguration;
        }        
    }

}
