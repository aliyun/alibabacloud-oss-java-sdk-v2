package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateAccessPoint operation.
 */
public final class CreateAccessPointRequest extends RequestModel { 
    private final String bucket;
    private final CreateAccessPointConfiguration createAccessPointConfiguration;

    private CreateAccessPointRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.createAccessPointConfiguration = builder.createAccessPointConfiguration;
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
    public CreateAccessPointConfiguration createAccessPointConfiguration() {
        return createAccessPointConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private CreateAccessPointConfiguration createAccessPointConfiguration;
    
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
        public Builder createAccessPointConfiguration(CreateAccessPointConfiguration value) {
            requireNonNull(value);
            this.createAccessPointConfiguration = value;
            return this;        
        }
        
        public CreateAccessPointRequest build() {
            return new CreateAccessPointRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.createAccessPointConfiguration = request.createAccessPointConfiguration;
        }        
    }

}
