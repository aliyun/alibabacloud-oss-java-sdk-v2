package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutPublicAccessBlock operation.
 */
public final class PutPublicAccessBlockRequest extends RequestModel { 
    private final PublicAccessBlockConfiguration publicAccessBlockConfiguration;

    private PutPublicAccessBlockRequest(Builder builder) {
        super(builder);
        this.publicAccessBlockConfiguration = builder.publicAccessBlockConfiguration;
    }
    
    /**
     * Request body.
     */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return publicAccessBlockConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private PublicAccessBlockConfiguration publicAccessBlockConfiguration;
    
        /**
        * Request body.
        */
        public Builder publicAccessBlockConfiguration(PublicAccessBlockConfiguration value) {
            requireNonNull(value);
            this.publicAccessBlockConfiguration = value;
            return this;        
        }
        
        public PutPublicAccessBlockRequest build() {
            return new PutPublicAccessBlockRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutPublicAccessBlockRequest request) {
            super(request);
            this.publicAccessBlockConfiguration = request.publicAccessBlockConfiguration;
        }        
    }

}
