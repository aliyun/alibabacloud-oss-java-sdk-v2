package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetDataPipelineConfiguration operation.
 */
public final class GetDataPipelineConfigurationRequest extends RequestModel {

    private GetDataPipelineConfigurationRequest(Builder builder) {
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

        private Builder(GetDataPipelineConfigurationRequest request) {
            super(request);
        }

        public Builder dataPipelineName(String value) {
            requireNonNull(value);
            this.parameters.put("dataPipelineName", value);
            return this;
        }

        public GetDataPipelineConfigurationRequest build() {
            return new GetDataPipelineConfigurationRequest(this);
        }
    }
}
