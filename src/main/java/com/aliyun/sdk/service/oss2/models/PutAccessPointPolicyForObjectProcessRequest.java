package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAccessPointPolicyForObjectProcess operation.
 */
public final class PutAccessPointPolicyForObjectProcessRequest extends RequestModel { 
    private final String bucket;
    private final BinaryData body;

    private PutAccessPointPolicyForObjectProcessRequest(Builder builder) {
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
     * The name of the Object FC Access Point.
     */
    public String accessPointForObjectProcessName() {
        String value = headers.get("x-oss-access-point-for-object-process-name");
        return value;
    }
    
    /**
     * The json format permission policies for an Object FC Access Point.
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
        * The name of the Object FC Access Point.
        */
        public Builder accessPointForObjectProcessName(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-access-point-for-object-process-name", value);
            return this;        
        }
    
        /**
        * The json format permission policies for an Object FC Access Point.
        */
        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;        
        }
        
        public PutAccessPointPolicyForObjectProcessRequest build() {
            return new PutAccessPointPolicyForObjectProcessRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAccessPointPolicyForObjectProcessRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.body = request.body;
        }        
    }

}
