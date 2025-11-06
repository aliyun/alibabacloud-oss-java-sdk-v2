package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutUserDefinedLogFieldsConfig operation.
 */
public final class PutUserDefinedLogFieldsConfigRequest extends RequestModel { 
    private final String bucket;
    private final UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration;

    private PutUserDefinedLogFieldsConfigRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.userDefinedLogFieldsConfiguration = builder.userDefinedLogFieldsConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The container that stores the specified log configurations.
     */
    public UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration() {
        return userDefinedLogFieldsConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The container that stores the specified log configurations.
        */
        public Builder userDefinedLogFieldsConfiguration(UserDefinedLogFieldsConfiguration value) {
            requireNonNull(value);
            this.userDefinedLogFieldsConfiguration = value;
            return this;        
        }
        
        public PutUserDefinedLogFieldsConfigRequest build() {
            return new PutUserDefinedLogFieldsConfigRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutUserDefinedLogFieldsConfigRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.userDefinedLogFieldsConfiguration = request.userDefinedLogFieldsConfiguration;
        }        
    }

}
