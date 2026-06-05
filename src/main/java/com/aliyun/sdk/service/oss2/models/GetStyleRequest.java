package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetStyle operation.
 */
public final class GetStyleRequest extends RequestModel { 
    private final String bucket;

    private GetStyleRequest(Builder builder) {
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
     * The name of the image style.
     */
    public String styleName() {
        String value = parameters.get("styleName");
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
        * The name of the image style.
        */
        public Builder styleName(String value) {
            requireNonNull(value);
            this.parameters.put("styleName", value);
            return this;        
        }
    
        
        public GetStyleRequest build() {
            return new GetStyleRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetStyleRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
