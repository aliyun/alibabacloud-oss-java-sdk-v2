package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutDataPipelineConfiguration operation.
 */
public final class PutDataPipelineConfigurationRequest extends RequestModel {
    private final PutDataPipelineConfigurationConfiguration putDataPipelineConfigurationConfiguration;

    private PutDataPipelineConfigurationRequest(Builder builder) {
        super(builder);
        this.putDataPipelineConfigurationConfiguration = builder.putDataPipelineConfigurationConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the data pipeline.
     */
    public String dataPipelineName() {
        return parameters.get("dataPipelineName");
    }

    /**
     * The role for the data pipeline.
     */
    public String role() {
        return parameters.get("role");
    }

    /**
     * The container of the request body.
     */
    public PutDataPipelineConfigurationConfiguration putDataPipelineConfigurationConfiguration() {
        return putDataPipelineConfigurationConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private PutDataPipelineConfigurationConfiguration putDataPipelineConfigurationConfiguration;

        /**
         * The name of the data pipeline.
         */
        public Builder dataPipelineName(String value) {
            requireNonNull(value);
            this.parameters.put("dataPipelineName", value);
            return this;
        }

        /**
         * The role for the data pipeline.
         */
        public Builder role(String value) {
            requireNonNull(value);
            this.parameters.put("role", value);
            return this;
        }

        /**
         * The container of the request body.
         */
        public Builder putDataPipelineConfigurationConfiguration(PutDataPipelineConfigurationConfiguration value) {
            requireNonNull(value);
            this.putDataPipelineConfigurationConfiguration = value;
            return this;
        }

        public PutDataPipelineConfigurationRequest build() {
            return new PutDataPipelineConfigurationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutDataPipelineConfigurationRequest request) {
            super(request);
            this.putDataPipelineConfigurationConfiguration = request.putDataPipelineConfigurationConfiguration;
        }
    }
}
