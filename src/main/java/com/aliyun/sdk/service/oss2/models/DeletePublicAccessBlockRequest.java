package com.aliyun.sdk.service.oss2.models;

/**
 * The request for the DeletePublicAccessBlock operation.
 */
public final class DeletePublicAccessBlockRequest extends RequestModel { 

    private DeletePublicAccessBlockRequest(Builder builder) {
        super(builder);
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
    
        
        public DeletePublicAccessBlockRequest build() {
            return new DeletePublicAccessBlockRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeletePublicAccessBlockRequest request) {
            super(request);
        }        
    }

}
