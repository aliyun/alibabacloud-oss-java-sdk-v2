package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateDataset operation.
 */
public final class CreateDatasetRequest extends RequestModel {
    private final String bucket;

    private CreateDatasetRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String datasetName() {
        return parameters.get("datasetName");
    }

    public String description() {
        return parameters.get("description");
    }

    public String templateId() {
        return parameters.get("templateId");
    }

    public String workflowParameters() {
        return parameters.get("workflowParameters");
    }

    public String datasetConfig() {
        return parameters.get("datasetConfig");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(CreateDatasetRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.parameters.put("datasetName", value);
            return this;
        }

        public Builder description(String value) {
            this.parameters.put("description", value);
            return this;
        }

        public Builder templateId(String value) {
            this.parameters.put("templateId", value);
            return this;
        }

        public Builder workflowParameters(List<WorkflowParameter> value) {
            this.parameters.put("workflowParameters", DataProcessParamHelper.toWorkflowParameters(value));
            return this;
        }

        public Builder workflowParameters(String value) {
            this.parameters.put("workflowParameters", value);
            return this;
        }

        public Builder datasetConfig(DatasetConfig value) {
            this.parameters.put("datasetConfig", DataProcessParamHelper.toDatasetConfig(value));
            return this;
        }

        public Builder datasetConfig(String value) {
            this.parameters.put("datasetConfig", value);
            return this;
        }

        public CreateDatasetRequest build() {
            return new CreateDatasetRequest(this);
        }
    }
}
