package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetCnameToken operation.
 */
public final class GetCnameTokenResult extends ResultModel { 

    /**
     * The container in which the CNAME token is stored.
     */
    public CnameToken cnameToken() {
        return (CnameToken)innerBody;
    } 
     

    GetCnameTokenResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetCnameTokenResult build() {
            return new GetCnameTokenResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetCnameTokenResult result) {
            super(result);
        }
    }
}
