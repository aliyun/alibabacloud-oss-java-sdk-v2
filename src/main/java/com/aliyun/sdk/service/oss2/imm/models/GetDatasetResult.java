package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetDataset operation.
 */
public final class GetDatasetResult extends ResultModel {
    private final Dataset dataset;

    GetDatasetResult(Builder builder) {
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

        private Builder(GetDatasetResult result) {
            super(result);
            this.dataset = result.dataset;
        }

        public Builder dataset(Dataset value) {
            this.dataset = value;
            return this;
        }

        public GetDatasetResult build() {
            return new GetDatasetResult(this);
        }
    }
}
