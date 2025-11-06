package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateBucketDataRedundancyTransition operation.
 */
public final class CreateBucketDataRedundancyTransitionRequest extends RequestModel { 
    private final String bucket;

    private CreateBucketDataRedundancyTransitionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The redundancy type to which you want to convert the bucket. You can only convert the redundancy type of a bucket from LRS to ZRS.
     */
    public String targetRedundancyType() {
        String value = parameters.get("x-oss-target-redundancy-type");
        return value;
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The redundancy type to which you want to convert the bucket. You can only convert the redundancy type of a bucket from LRS to ZRS.
        */
        public Builder targetRedundancyType(String value) {
            requireNonNull(value);
            this.parameters.put("x-oss-target-redundancy-type", value);
            return this;        
        }
    
        /**
        * The redundancy type to which you want to convert the bucket. You can only convert the redundancy type of a bucket from LRS to ZRS.
        */
        public Builder targetRedundancyType(DataRedundancyType value) {
            requireNonNull(value);
            this.parameters.put("x-oss-target-redundancy-type", value.toString());
            return this;
        }
        
        public CreateBucketDataRedundancyTransitionRequest build() {
            return new CreateBucketDataRedundancyTransitionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateBucketDataRedundancyTransitionRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}