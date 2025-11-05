package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteUserDefinedLogFieldsConfig operation.
 */
public final class DeleteUserDefinedLogFieldsConfigResult extends ResultModel { 

    DeleteUserDefinedLogFieldsConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteUserDefinedLogFieldsConfigResult build() {
            return new DeleteUserDefinedLogFieldsConfigResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteUserDefinedLogFieldsConfigResult result) {
            super(result);
        }
    }
}
