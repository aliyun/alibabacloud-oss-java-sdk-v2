package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateAccessPointForObjectProcess operation.
 */
public final class CreateAccessPointForObjectProcessRequest extends RequestModel { 
    private final String bucket;
    private final CreateAccessPointForObjectProcessConfiguration createAccessPointForObjectProcessConfiguration;

    private CreateAccessPointForObjectProcessRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.createAccessPointForObjectProcessConfiguration = builder.createAccessPointForObjectProcessConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The name of the Object FC Access Point.
     */
    public String accessPointForObjectProcessName() {
        String value = headers.get("x-oss-access-point-for-object-process-name");
        return value;
    }
    
    /**
     * The request body.
     */
    public CreateAccessPointForObjectProcessConfiguration createAccessPointForObjectProcessConfiguration() {
        return createAccessPointForObjectProcessConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private CreateAccessPointForObjectProcessConfiguration createAccessPointForObjectProcessConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The name of the Object FC Access Point.
        */
        public Builder accessPointForObjectProcessName(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-access-point-for-object-process-name", value);
            return this;        
        }
    
        /**
        * The request body.
        */
        public Builder createAccessPointForObjectProcessConfiguration(CreateAccessPointForObjectProcessConfiguration value) {
            requireNonNull(value);
            this.createAccessPointForObjectProcessConfiguration = value;
            return this;        
        }
        
        public CreateAccessPointForObjectProcessRequest build() {
            return new CreateAccessPointForObjectProcessRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointForObjectProcessRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.createAccessPointForObjectProcessConfiguration = request.createAccessPointForObjectProcessConfiguration;
        }        
    }

}
