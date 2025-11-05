package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetUserDefinedLogFieldsConfig operation.
 */
public final class GetUserDefinedLogFieldsConfigResult extends ResultModel { 

    /**
     * The container for the user-defined logging configuration.
     */
    public UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration() {
        return (UserDefinedLogFieldsConfiguration)innerBody;
    } 
     

    GetUserDefinedLogFieldsConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetUserDefinedLogFieldsConfigResult build() {
            return new GetUserDefinedLogFieldsConfigResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetUserDefinedLogFieldsConfigResult result) {
            super(result);
        }
    }
}
