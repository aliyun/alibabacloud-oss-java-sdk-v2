package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateCnameToken operation.
 */
public final class CreateCnameTokenResult extends ResultModel { 

    /**
     * The container in which the CNAME token is stored.
     */
    public CnameToken cnameToken() {
        return (CnameToken)innerBody;
    } 
     

    CreateCnameTokenResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CreateCnameTokenResult build() {
            return new CreateCnameTokenResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateCnameTokenResult result) {
            super(result);
        }
    }
}
