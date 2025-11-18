package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketReplicationProgress operation.
 */
public final class GetBucketReplicationProgressRequest extends RequestModel { 
    private final String bucket;

    private GetBucketReplicationProgressRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucekt.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The ID of the data replication rule. You can call the GetBucketReplication operation to query the ID.
     */
    public String ruleId() {
        String value = parameters.get("rule-id");
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
        * The name of the bucekt.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The ID of the data replication rule. You can call the GetBucketReplication operation to query the ID.
        */
        public Builder ruleId(String value) {
            requireNonNull(value);
            this.parameters.put("rule-id", value);
            return this;        
        }
    
        
        public GetBucketReplicationProgressRequest build() {
            return new GetBucketReplicationProgressRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketReplicationProgressRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
