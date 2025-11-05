package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketPolicy operation.
 */
public final class PutBucketPolicyRequest extends RequestModel { 
    private final String bucket;
    private final BinaryData body;

    private PutBucketPolicyRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.body = builder.body;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The request body.
     */
    public BinaryData body() {
        return body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private BinaryData body;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The request body.
        */
        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;        
        }
        
        public PutBucketPolicyRequest build() {
            return new PutBucketPolicyRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketPolicyRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.body = request.body;
        }        
    }

}
