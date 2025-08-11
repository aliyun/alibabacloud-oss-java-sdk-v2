package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketVersioning operation.
 */
public final class PutBucketVersioningRequest extends RequestModel { 
    private final String bucket;
    private final VersioningConfiguration versioningConfiguration;

    private PutBucketVersioningRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.versioningConfiguration = builder.versioningConfiguration;
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
    public VersioningConfiguration versioningConfiguration() {
        return versioningConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private VersioningConfiguration versioningConfiguration;
    
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
        public Builder versioningConfiguration(VersioningConfiguration value) {
            requireNonNull(value);
            this.versioningConfiguration = value;
            return this;        
        }
        
        public PutBucketVersioningRequest build() {
            return new PutBucketVersioningRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketVersioningRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.versioningConfiguration = request.versioningConfiguration;
        }        
    }

}
