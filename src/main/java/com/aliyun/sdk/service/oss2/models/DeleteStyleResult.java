package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteStyle operation.
 */
public final class DeleteStyleResult extends ResultModel { 

    DeleteStyleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteStyleResult build() {
            return new DeleteStyleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteStyleResult result) {
            super(result);
        }
    }
}
