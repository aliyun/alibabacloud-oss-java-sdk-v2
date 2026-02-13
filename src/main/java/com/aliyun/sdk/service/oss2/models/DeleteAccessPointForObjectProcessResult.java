package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteAccessPointForObjectProcess operation.
 */
public final class DeleteAccessPointForObjectProcessResult extends ResultModel { 

    DeleteAccessPointForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteAccessPointForObjectProcessResult build() {
            return new DeleteAccessPointForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteAccessPointForObjectProcessResult result) {
            super(result);
        }
    }
}
