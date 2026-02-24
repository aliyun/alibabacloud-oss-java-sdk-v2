package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetAccessPointPolicyForObjectProcess operation.
 */
public final class GetAccessPointPolicyForObjectProcessRequest extends RequestModel { 
    private final String bucket;

    private GetAccessPointPolicyForObjectProcessRequest(Builder builder) {
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
     * The name of the Object FC Access Point.
     */
    public String accessPointForObjectProcessName() {
        String value = headers.get("x-oss-access-point-for-object-process-name");
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
        * The name of the Object FC Access Point.
        */
        public Builder accessPointForObjectProcessName(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-access-point-for-object-process-name", value);
            return this;        
        }
    
        
        public GetAccessPointPolicyForObjectProcessRequest build() {
            return new GetAccessPointPolicyForObjectProcessRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointPolicyForObjectProcessRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
