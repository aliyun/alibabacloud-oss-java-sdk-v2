package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteVectors operation.
 */
public final class DeleteVectorsResult extends ResultModel {

    DeleteVectorsResult(Builder builder) {
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

        private Builder(DeleteVectorsResult result) {
            super(result);
        }

        public DeleteVectorsResult build() {
            return new DeleteVectorsResult(this);
        }
    }
}
