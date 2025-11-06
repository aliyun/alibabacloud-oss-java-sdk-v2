package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;

/**
 * The result for the GetBucketPolicy operation.
 */
public final class GetBucketPolicyResult extends ResultModel { 

    /**
     * body
     */
    public BinaryData body() {
        return (BinaryData)innerBody;
    } 
     

    GetBucketPolicyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketPolicyResult build() {
            return new GetBucketPolicyResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketPolicyResult result) {
            super(result);
        }
    }
}
