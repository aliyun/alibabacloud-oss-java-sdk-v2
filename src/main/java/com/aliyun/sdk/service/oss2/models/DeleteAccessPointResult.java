package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteAccessPoint operation.
 */
public final class DeleteAccessPointResult extends ResultModel { 

    DeleteAccessPointResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteAccessPointResult build() {
            return new DeleteAccessPointResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteAccessPointResult result) {
            super(result);
        }
    }
}
