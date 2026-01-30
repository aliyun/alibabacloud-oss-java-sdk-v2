package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketArchiveDirectRead operation.
 */
public final class PutBucketArchiveDirectReadRequest extends RequestModel { 
    private final String bucket;
    private final ArchiveDirectReadConfiguration archiveDirectReadConfiguration;

    private PutBucketArchiveDirectReadRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.archiveDirectReadConfiguration = builder.archiveDirectReadConfiguration;
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
    public ArchiveDirectReadConfiguration archiveDirectReadConfiguration() {
        return archiveDirectReadConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private ArchiveDirectReadConfiguration archiveDirectReadConfiguration;
    
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
        public Builder archiveDirectReadConfiguration(ArchiveDirectReadConfiguration value) {
            requireNonNull(value);
            this.archiveDirectReadConfiguration = value;
            return this;        
        }
        
        public PutBucketArchiveDirectReadRequest build() {
            return new PutBucketArchiveDirectReadRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketArchiveDirectReadRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.archiveDirectReadConfiguration = request.archiveDirectReadConfiguration;
        }        
    }

}
