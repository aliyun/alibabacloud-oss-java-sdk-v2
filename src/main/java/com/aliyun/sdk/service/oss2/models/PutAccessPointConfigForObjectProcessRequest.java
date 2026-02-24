package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAccessPointConfigForObjectProcess operation.
 */
public final class PutAccessPointConfigForObjectProcessRequest extends RequestModel { 
    private final String bucket;
    private final PutAccessPointConfigForObjectProcessConfiguration putAccessPointConfigForObjectProcessConfiguration;

    private PutAccessPointConfigForObjectProcessRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.putAccessPointConfigForObjectProcessConfiguration = builder.putAccessPointConfigForObjectProcessConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The name of the Object FC Access Point. The name of an Object FC Access Point must meet the following requirements:The name cannot exceed 63 characters in length.The name can contain only lowercase letters, digits, and hyphens (-) and cannot start or end with a hyphen (-).The name must be unique in the current region.
     */
    public String accessPointForObjectProcessName() {
        String value = headers.get("x-oss-access-point-for-object-process-name");
        return value;
    }
    
    /**
     * The request body.
     */
    public PutAccessPointConfigForObjectProcessConfiguration putAccessPointConfigForObjectProcessConfiguration() {
        return putAccessPointConfigForObjectProcessConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private PutAccessPointConfigForObjectProcessConfiguration putAccessPointConfigForObjectProcessConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The name of the Object FC Access Point. The name of an Object FC Access Point must meet the following requirements:The name cannot exceed 63 characters in length.The name can contain only lowercase letters, digits, and hyphens (-) and cannot start or end with a hyphen (-).The name must be unique in the current region.
        */
        public Builder accessPointForObjectProcessName(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-access-point-for-object-process-name", value);
            return this;        
        }
    
        /**
        * The request body.
        */
        public Builder putAccessPointConfigForObjectProcessConfiguration(PutAccessPointConfigForObjectProcessConfiguration value) {
            requireNonNull(value);
            this.putAccessPointConfigForObjectProcessConfiguration = value;
            return this;        
        }
        
        public PutAccessPointConfigForObjectProcessRequest build() {
            return new PutAccessPointConfigForObjectProcessRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAccessPointConfigForObjectProcessRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.putAccessPointConfigForObjectProcessConfiguration = request.putAccessPointConfigForObjectProcessConfiguration;
        }        
    }

}
