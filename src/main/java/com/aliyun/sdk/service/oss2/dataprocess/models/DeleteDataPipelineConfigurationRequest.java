package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteDataPipelineConfiguration operation.
 */
public final class DeleteDataPipelineConfigurationRequest extends RequestModel {

    private DeleteDataPipelineConfigurationRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String dataPipelineName() {
        return parameters.get("dataPipelineName");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DeleteDataPipelineConfigurationRequest request) {
            super(request);
        }

        public Builder dataPipelineName(String value) {
            this.parameters.put("dataPipelineName", value);
            return this;
        }

        public DeleteDataPipelineConfigurationRequest build() {
            return new DeleteDataPipelineConfigurationRequest(this);
        }
    }
}
