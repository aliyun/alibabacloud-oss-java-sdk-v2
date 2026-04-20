package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutDataPipelineConfiguration operation.
 */
public final class PutDataPipelineConfigurationResult extends ResultModel {

    public String requestId() {
        PutDataPipelineConfigurationResponseBody body = (PutDataPipelineConfigurationResponseBody) innerBody;
        return body != null ? body.requestId() : null;
    }

    PutDataPipelineConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutDataPipelineConfigurationResult build() {
            return new PutDataPipelineConfigurationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutDataPipelineConfigurationResult result) {
            super(result);
        }
    }
}
