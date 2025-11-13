package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetCnameToken operation.
 */
public final class GetCnameTokenRequest extends RequestModel { 
    private final String bucket;

    private GetCnameTokenRequest(Builder builder) {
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
     * The name of the CNAME record that is mapped to the bucket.
     */
    public String cname() {
        String value = parameters.get("cname");
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
        * The name of the CNAME record that is mapped to the bucket.
        */
        public Builder cname(String value) {
            requireNonNull(value);
            this.parameters.put("cname", value);
            return this;        
        }
    
        
        public GetCnameTokenRequest build() {
            return new GetCnameTokenRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetCnameTokenRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
