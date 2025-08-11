package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketVersioning operation.
 */
public final class GetBucketVersioningRequest extends RequestModel { 
    private final String bucket;

    private GetBucketVersioningRequest(Builder builder) {
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
    
        
        public GetBucketVersioningRequest build() {
            return new GetBucketVersioningRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketVersioningRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
