package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;

/**
 * The result for the GetAccessPointPolicy operation.
 */
public final class GetAccessPointPolicyResult extends ResultModel { 

    /**
     * access point policy
     */
    public BinaryData body() {
        return (BinaryData)innerBody;
    } 
     

    GetAccessPointPolicyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetAccessPointPolicyResult build() {
            return new GetAccessPointPolicyResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointPolicyResult result) {
            super(result);
        }
    }
}
