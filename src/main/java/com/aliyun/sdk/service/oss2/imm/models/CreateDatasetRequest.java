package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateDataset operation.
 */
public final class CreateDatasetRequest extends RequestModel {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

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

    public Long datasetMaxBindCount() {
        return ConvertUtils.toLongOrNull(parameters.get("datasetMaxBindCount"));
    }

    public Long datasetMaxFileCount() {
        return ConvertUtils.toLongOrNull(parameters.get("datasetMaxFileCount"));
    }

    public Long datasetMaxEntityCount() {
        return ConvertUtils.toLongOrNull(parameters.get("datasetMaxEntityCount"));
    }

    public Long datasetMaxRelationCount() {
        return ConvertUtils.toLongOrNull(parameters.get("datasetMaxRelationCount"));
    }

    public Long datasetMaxTotalFileSize() {
        return ConvertUtils.toLongOrNull(parameters.get("datasetMaxTotalFileSize"));
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

    private static String toJson(Object value) {
        try {
            return JSON_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        public Builder datasetMaxBindCount(Long value) {
            this.parameters.put("datasetMaxBindCount", value.toString());
            return this;
        }

        public Builder datasetMaxFileCount(Long value) {
            this.parameters.put("datasetMaxFileCount", value.toString());
            return this;
        }

        public Builder datasetMaxEntityCount(Long value) {
            this.parameters.put("datasetMaxEntityCount", value.toString());
            return this;
        }

        public Builder datasetMaxRelationCount(Long value) {
            this.parameters.put("datasetMaxRelationCount", value.toString());
            return this;
        }

        public Builder datasetMaxTotalFileSize(Long value) {
            this.parameters.put("datasetMaxTotalFileSize", value.toString());
            return this;
        }

        public Builder workflowParameters(List<WorkflowParameter> value) {
            this.parameters.put("workflowParameters", toJson(value));
            return this;
        }

        public Builder datasetConfig(DatasetConfig value) {
            this.parameters.put("datasetConfig", toJson(value));
            return this;
        }

        public CreateDatasetRequest build() {
            return new CreateDatasetRequest(this);
        }
    }
}
