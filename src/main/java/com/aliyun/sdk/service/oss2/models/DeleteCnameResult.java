package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteCname operation.
 */
public final class DeleteCnameResult extends ResultModel { 

    DeleteCnameResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteCnameResult build() {
            return new DeleteCnameResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteCnameResult result) {
            super(result);
        }
    }
}
