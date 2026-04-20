package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutDataPipelineConfiguration operation.
 */
public final class PutDataPipelineConfigurationRequest extends RequestModel {
    private final String bucket;

    private PutDataPipelineConfigurationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String dataPipelineName() {
        return parameters.get("dataPipelineName");
    }

    public String role() {
        return parameters.get("role");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(PutDataPipelineConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder dataPipelineName(String value) {
            requireNonNull(value);
            this.parameters.put("dataPipelineName", value);
            return this;
        }

        public Builder role(String value) {
            requireNonNull(value);
            this.parameters.put("role", value);
            return this;
        }

        public Builder dataPipelineDescription(String value) {
            this.parameters.put("DataPipelineDescription", value);
            return this;
        }

        public Builder sources(String value) {
            this.parameters.put("Sources", value);
            return this;
        }

        public Builder dataPipelineEmbeddingConfiguration(String value) {
            this.parameters.put("DataPipelineEmbeddingConfiguration", value);
            return this;
        }

        public Builder destination(String value) {
            this.parameters.put("Destination", value);
            return this;
        }

        public Builder dataPipelineError(String value) {
            this.parameters.put("DataPipelineError", value);
            return this;
        }

        public PutDataPipelineConfigurationRequest build() {
            return new PutDataPipelineConfigurationRequest(this);
        }
    }
}
