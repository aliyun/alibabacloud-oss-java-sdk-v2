package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the UpdateDataset operation.
 */
public final class UpdateDatasetResult extends ResultModel {

    public Dataset dataset() {
        UpdateDatasetResponseBody body = (UpdateDatasetResponseBody) innerBody;
        return body != null ? body.dataset() : null;
    }

    UpdateDatasetResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public UpdateDatasetResult build() {
            return new UpdateDatasetResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(UpdateDatasetResult result) {
            super(result);
        }
    }
}
