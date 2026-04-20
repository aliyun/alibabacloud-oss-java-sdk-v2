package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PauseDataPipeline operation.
 */
public final class PauseDataPipelineResult extends ResultModel {

    public String requestId() {
        PauseDataPipelineResponseBody body = (PauseDataPipelineResponseBody) innerBody;
        return body != null ? body.requestId() : null;
    }

    PauseDataPipelineResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PauseDataPipelineResult build() {
            return new PauseDataPipelineResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PauseDataPipelineResult result) {
            super(result);
        }
    }
}
