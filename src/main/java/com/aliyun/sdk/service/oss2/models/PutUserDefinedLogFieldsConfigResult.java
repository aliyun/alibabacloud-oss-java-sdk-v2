package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutUserDefinedLogFieldsConfig operation.
 */
public final class PutUserDefinedLogFieldsConfigResult extends ResultModel { 

    PutUserDefinedLogFieldsConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutUserDefinedLogFieldsConfigResult build() {
            return new PutUserDefinedLogFieldsConfigResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutUserDefinedLogFieldsConfigResult result) {
            super(result);
        }
    }
}
