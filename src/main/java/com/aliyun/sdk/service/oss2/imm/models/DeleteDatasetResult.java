package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteDataset operation.
 */
public final class DeleteDatasetResult extends ResultModel {

    DeleteDatasetResult(Builder builder) {
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

        private Builder(DeleteDatasetResult result) {
            super(result);
        }

        public DeleteDatasetResult build() {
            return new DeleteDatasetResult(this);
        }
    }
}
