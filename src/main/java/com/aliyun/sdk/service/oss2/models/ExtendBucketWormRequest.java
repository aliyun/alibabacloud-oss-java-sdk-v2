package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ExtendBucketWorm operation.
 */
public final class ExtendBucketWormRequest extends RequestModel { 
    private final String bucket;
    private final ExtendWormConfiguration extendWormConfiguration;

    private ExtendBucketWormRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.extendWormConfiguration = builder.extendWormConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The ID of the retention policy.  If the ID of the retention policy that specifies the number of days for which objects can be retained does not exist, the HTTP status code 404 is returned.
     */
    public String wormId() {
        String value = parameters.get("wormId");
        return value;
    }
    
    /**
     * The container of the request body.
     */
    public ExtendWormConfiguration extendWormConfiguration() {
        return extendWormConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private ExtendWormConfiguration extendWormConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The ID of the retention policy.  If the ID of the retention policy that specifies the number of days for which objects can be retained does not exist, the HTTP status code 404 is returned.
        */
        public Builder wormId(String value) {
            requireNonNull(value);
            this.parameters.put("wormId", value);
            return this;        
        }
    
        /**
        * The container of the request body.
        */
        public Builder extendWormConfiguration(ExtendWormConfiguration value) {
            requireNonNull(value);
            this.extendWormConfiguration = value;
            return this;        
        }
        
        public ExtendBucketWormRequest build() {
            return new ExtendBucketWormRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(ExtendBucketWormRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.extendWormConfiguration = request.extendWormConfiguration;
        }        
    }

}
