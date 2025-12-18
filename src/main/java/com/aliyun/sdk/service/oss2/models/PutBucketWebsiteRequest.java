package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import java.time.Instant;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketWebsite operation.
 */
public final class PutBucketWebsiteRequest extends RequestModel { 
    private final String bucket;
    private final WebsiteConfiguration websiteConfiguration;

    private PutBucketWebsiteRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.websiteConfiguration = builder.websiteConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The request body schema.
     */
    public WebsiteConfiguration websiteConfiguration() {
        return websiteConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private WebsiteConfiguration websiteConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The request body schema.
        */
        public Builder websiteConfiguration(WebsiteConfiguration value) {
            requireNonNull(value);
            this.websiteConfiguration = value;
            return this;        
        }
        
        public PutBucketWebsiteRequest build() {
            return new PutBucketWebsiteRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketWebsiteRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.websiteConfiguration = request.websiteConfiguration;
        }        
    }

}
