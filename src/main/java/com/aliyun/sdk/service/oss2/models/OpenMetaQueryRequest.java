package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the OpenMetaQuery operation.
 */
public final class OpenMetaQueryRequest extends RequestModel { 
    private final String bucket;
    private final MetaQueryOpenRequest metaQueryOpenRequest;

    private OpenMetaQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.metaQueryOpenRequest = builder.metaQueryOpenRequest;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * Query mode. Values:
     *
     * basic (default): scalar query
     *
     * semantic: vector query
     */
    public String mode() {
        String value = parameters.get("mode");
        return value;
    }
    
    /**
     * Specify the RAM role name used to access OSS service, supporting granting permissions to roles in console for secure access.
     */
    public String role() {
        String value = parameters.get("role");
        return value;
    }
    
    /**
     * Interface request body parameters
     */
    public MetaQueryOpenRequest metaQueryOpenRequest() {
        return metaQueryOpenRequest;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private MetaQueryOpenRequest metaQueryOpenRequest;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * Query mode. Values:
        *
        * basic (default): scalar query
        *
        * semantic: vector query
        */
        public Builder mode(String value) {
            requireNonNull(value);
            this.parameters.put("mode", value);
            return this;        
        }
    
        /**
        * Specify the RAM role name used to access OSS service, supporting granting permissions to roles in console for secure access.
        */
        public Builder role(String value) {
            requireNonNull(value);
            this.parameters.put("role", value);
            return this;        
        }
    
        /**
        * Interface request body parameters
        */
        public Builder metaQueryOpenRequest(MetaQueryOpenRequest value) {
            requireNonNull(value);
            this.metaQueryOpenRequest = value;
            return this;        
        }
        
        public OpenMetaQueryRequest build() {
            return new OpenMetaQueryRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(OpenMetaQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.metaQueryOpenRequest = request.metaQueryOpenRequest;
        }        
    }

}