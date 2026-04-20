package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetDataPipelineConfiguration operation.
 */
public final class GetDataPipelineConfigurationResult extends ResultModel {

    public DataPipelineConfiguration dataPipelineConfiguration() {
        GetDataPipelineConfigurationResponseBody body = (GetDataPipelineConfigurationResponseBody) innerBody;
        return body != null ? body.dataPipelineConfiguration() : null;
    }

    GetDataPipelineConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetDataPipelineConfigurationResult build() {
            return new GetDataPipelineConfigurationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetDataPipelineConfigurationResult result) {
            super(result);
        }
    }
}
