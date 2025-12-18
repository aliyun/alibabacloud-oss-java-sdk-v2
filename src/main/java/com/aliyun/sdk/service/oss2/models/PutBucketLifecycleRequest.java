package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketLifecycle operation.
 */
public final class PutBucketLifecycleRequest extends RequestModel { 
    private final String bucket;
    private final LifecycleConfiguration lifecycleConfiguration;

    private PutBucketLifecycleRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.lifecycleConfiguration = builder.lifecycleConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * Specifies whether to allow overlapped prefixes. Valid values:true: Overlapped prefixes are allowed.false: Overlapped prefixes are not allowed.
     */
    public String allowSameActionOverlap() {
        String value = headers.get("x-oss-allow-same-action-overlap");
        return value;
    }
    
    /**
     * The container of the request body.
     */
    public LifecycleConfiguration lifecycleConfiguration() {
        return lifecycleConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private LifecycleConfiguration lifecycleConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * Specifies whether to allow overlapped prefixes. Valid values:true: Overlapped prefixes are allowed.false: Overlapped prefixes are not allowed.
        */
        public Builder allowSameActionOverlap(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-allow-same-action-overlap", value);
            return this;        
        }
    
        /**
        * The container of the request body.
        */
        public Builder lifecycleConfiguration(LifecycleConfiguration value) {
            requireNonNull(value);
            this.lifecycleConfiguration = value;
            return this;        
        }
        
        public PutBucketLifecycleRequest build() {
            return new PutBucketLifecycleRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketLifecycleRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.lifecycleConfiguration = request.lifecycleConfiguration;
        }        
    }

}
