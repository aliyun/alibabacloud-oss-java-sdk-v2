package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetPublicAccessBlock operation.
 */
public final class GetPublicAccessBlockResult extends ResultModel { 

    /**
     * The container in which the Block Public Access configurations are stored.
     */
    public PublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return (PublicAccessBlockConfiguration)innerBody;
    } 
     

    GetPublicAccessBlockResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetPublicAccessBlockResult build() {
            return new GetPublicAccessBlockResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetPublicAccessBlockResult result) {
            super(result);
        }
    }
}
