package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the UpdateDataset operation.
 */
public final class UpdateDatasetResult extends ResultModel {
    private final Dataset dataset;

    UpdateDatasetResult(Builder builder) {
        super(builder);
        this.dataset = builder.dataset;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Dataset dataset() {
        return dataset;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private Dataset dataset;

        private Builder() {
            super();
        }

        private Builder(UpdateDatasetResult result) {
            super(result);
            this.dataset = result.dataset;
        }

        public Builder dataset(Dataset value) {
            this.dataset = value;
            return this;
        }

        public UpdateDatasetResult build() {
            return new UpdateDatasetResult(this);
        }
    }
}
