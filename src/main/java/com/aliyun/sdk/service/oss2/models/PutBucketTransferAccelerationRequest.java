package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import java.time.Instant;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketTransferAcceleration operation.
 */
public final class PutBucketTransferAccelerationRequest extends RequestModel { 
    private final String bucket;
    private final TransferAccelerationConfiguration transferAccelerationConfiguration;

    private PutBucketTransferAccelerationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.transferAccelerationConfiguration = builder.transferAccelerationConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The container of the request body.
     */
    public TransferAccelerationConfiguration transferAccelerationConfiguration() {
        return transferAccelerationConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private TransferAccelerationConfiguration transferAccelerationConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The container of the request body.
        */
        public Builder transferAccelerationConfiguration(TransferAccelerationConfiguration value) {
            requireNonNull(value);
            this.transferAccelerationConfiguration = value;
            return this;        
        }
        
        public PutBucketTransferAccelerationRequest build() {
            return new PutBucketTransferAccelerationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketTransferAccelerationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.transferAccelerationConfiguration = request.transferAccelerationConfiguration;
        }        
    }

}
