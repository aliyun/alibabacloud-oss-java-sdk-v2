package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAccessPointPolicy operation.
 */
public final class PutAccessPointPolicyRequest extends RequestModel { 
    private final String bucket;
    private final BinaryData body;

    private PutAccessPointPolicyRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.body = builder.body;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The name of the access point.
     */
    public String accessPointName() {
        String value = headers.get("x-oss-access-point-name");
        return value;
    }
    
    /**
     * The configurations of the access point policy.
     */
    public BinaryData body() {
        return body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private BinaryData body;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The name of the access point.
        */
        public Builder accessPointName(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-access-point-name", value);
            return this;        
        }
    
        /**
        * The configurations of the access point policy.
        */
        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;        
        }
        
        public PutAccessPointPolicyRequest build() {
            return new PutAccessPointPolicyRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAccessPointPolicyRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.body = request.body;
        }        
    }

}
