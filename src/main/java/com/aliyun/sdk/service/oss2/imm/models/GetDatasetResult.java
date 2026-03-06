package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetDataset operation.
 */
public final class GetDatasetResult extends ResultModel {

    public Dataset dataset() {
        GetDatasetResponseBody body = (GetDatasetResponseBody) innerBody;
        return body != null ? body.dataset() : null;
    }

    GetDatasetResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetDatasetResult build() {
            return new GetDatasetResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetDatasetResult result) {
            super(result);
        }
    }
}
