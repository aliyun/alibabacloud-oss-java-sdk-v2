package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the CreateDataset operation.
 */
public final class CreateDatasetResult extends ResultModel {

    public Dataset dataset() {
        CreateDatasetResponseBody body = (CreateDatasetResponseBody) innerBody;
        return body != null ? body.dataset() : null;
    }

    CreateDatasetResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CreateDatasetResult build() {
            return new CreateDatasetResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateDatasetResult result) {
            super(result);
        }
    }
}
