package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteVectorIndex operation.
 */
public final class DeleteVectorIndexResult extends ResultModel {

    DeleteVectorIndexResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DeleteVectorIndexResult result) {
            super(result);
        }

        public DeleteVectorIndexResult build() {
            return new DeleteVectorIndexResult(this);
        }
    }
}
