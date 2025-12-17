package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketEncryption operation.
 */
public final class GetBucketEncryptionResult extends ResultModel { 

    /**
     * The container that stores server-side encryption rules.
     */
    public ServerSideEncryptionRule serverSideEncryptionRule() {
        return (ServerSideEncryptionRule)innerBody;
    } 
     

    GetBucketEncryptionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketEncryptionResult build() {
            return new GetBucketEncryptionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketEncryptionResult result) {
            super(result);
        }
    }
}
