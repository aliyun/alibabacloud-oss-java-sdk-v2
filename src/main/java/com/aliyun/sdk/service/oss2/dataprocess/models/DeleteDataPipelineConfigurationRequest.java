package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteDataPipelineConfiguration operation.
 */
public final class DeleteDataPipelineConfigurationRequest extends RequestModel {
    private final String bucket;

    private DeleteDataPipelineConfigurationRequest(Builder builder) {
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(DeleteDataPipelineConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
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
