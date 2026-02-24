package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;

/**
 * The result for the GetAccessPointPolicyForObjectProcess operation.
 */
public final class GetAccessPointPolicyForObjectProcessResult extends ResultModel { 

    /**
     * body
     */
    public BinaryData body() {
        return (BinaryData)innerBody;
    } 
     

    GetAccessPointPolicyForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetAccessPointPolicyForObjectProcessResult build() {
            return new GetAccessPointPolicyForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointPolicyForObjectProcessResult result) {
            super(result);
        }
    }
}
