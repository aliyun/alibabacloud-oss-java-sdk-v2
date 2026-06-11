package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import java.time.Instant;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketHttpsConfig operation.
 */
public final class PutBucketHttpsConfigRequest extends RequestModel { 
    private final String bucket;
    private final HttpsConfiguration httpsConfiguration;

    private PutBucketHttpsConfigRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.httpsConfiguration = builder.httpsConfiguration;
    }
    
    /**
     * This name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The request body schema.
     */
    public HttpsConfiguration httpsConfiguration() {
        return httpsConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private HttpsConfiguration httpsConfiguration;
    
        /**
        * This name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The request body schema.
        */
        public Builder httpsConfiguration(HttpsConfiguration value) {
            requireNonNull(value);
            this.httpsConfiguration = value;
            return this;        
        }
        
        public PutBucketHttpsConfigRequest build() {
            return new PutBucketHttpsConfigRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketHttpsConfigRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.httpsConfiguration = request.httpsConfiguration;
        }        
    }

}
