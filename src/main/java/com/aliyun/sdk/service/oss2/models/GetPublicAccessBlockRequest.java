package com.aliyun.sdk.service.oss2.models;

/**
 * The request for the GetPublicAccessBlock operation.
 */
public final class GetPublicAccessBlockRequest extends RequestModel { 

    private GetPublicAccessBlockRequest(Builder builder) {
        super(builder);
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
    
        
        public GetPublicAccessBlockRequest build() {
            return new GetPublicAccessBlockRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetPublicAccessBlockRequest request) {
            super(request);
        }        
    }

}
