package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import java.time.Instant;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketTransferAcceleration operation.
 */
public final class GetBucketTransferAccelerationRequest extends RequestModel { 
    private final String bucket;

    private GetBucketTransferAccelerationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        
        public GetBucketTransferAccelerationRequest build() {
            return new GetBucketTransferAccelerationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketTransferAccelerationRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
