package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the RestartDataPipeline operation.
 */
public final class RestartDataPipelineResult extends ResultModel {

    public String requestId() {
        RestartDataPipelineResponseBody body = (RestartDataPipelineResponseBody) innerBody;
        return body != null ? body.requestId() : null;
    }

    RestartDataPipelineResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public RestartDataPipelineResult build() {
            return new RestartDataPipelineResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(RestartDataPipelineResult result) {
            super(result);
        }
    }
}
