package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteDataPipelineConfiguration operation.
 */
public final class DeleteDataPipelineConfigurationResult extends ResultModel {

    public String requestId() {
        DeleteDataPipelineConfigurationResponseBody body = (DeleteDataPipelineConfigurationResponseBody) innerBody;
        return body != null ? body.requestId() : null;
    }

    DeleteDataPipelineConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteDataPipelineConfigurationResult build() {
            return new DeleteDataPipelineConfigurationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteDataPipelineConfigurationResult result) {
            super(result);
        }
    }
}
