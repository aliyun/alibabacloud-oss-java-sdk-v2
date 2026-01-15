package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DoMetaQuery operation.
 */
public final class DoMetaQueryRequest extends RequestModel { 
    private final String bucket;
    private final MetaQuery metaQuery;

    private DoMetaQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.metaQuery = builder.metaQuery;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * Specifies the query mode.
     * - basic: scalar query (default)
     * - semantic: vector query
     */
    public String mode() {
        String value = parameters.get("mode");
        return value;
    }
    
    /**
     * The request body schema.
     */
    public MetaQuery metaQuery() {
        return metaQuery;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private MetaQuery metaQuery;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * Specifies the query mode.
        * - basic: scalar query (default)
        * - semantic: vector query
        */
        public Builder mode(String value) {
            requireNonNull(value);
            this.parameters.put("mode", value);
            return this;        
        }
    
        /**
        * The request body schema.
        */
        public Builder metaQuery(MetaQuery value) {
            requireNonNull(value);
            this.metaQuery = value;
            return this;        
        }
        
        public DoMetaQueryRequest build() {
            return new DoMetaQueryRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DoMetaQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.metaQuery = request.metaQuery;
        }        
    }

}
